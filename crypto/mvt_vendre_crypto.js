// Importer Firebase
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getFirestore, collection, addDoc, doc, runTransaction, serverTimestamp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-firestore.js";

// Configuration Firebase corrigée
const firebaseConfig = {
    apiKey: "AIzaSyDwfQNfgWkGWpd0Fjib_VHRXuk75m51zds",
    authDomain: "cryptos-d4f6b.firebaseapp.com",
    projectId: "cryptos-d4f6b",
    storageBucket: "cryptos-d4f6b.appspot.com",  // Correction ici
    messagingSenderId: "938286766542",
    appId: "1:938286766542:web:6240cb83b29ecd697cb39f",
    measurementId: "G-QHQLP3ZNDY"
};

// Initialisation Firebase
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// Fonction pour obtenir le prochain ID unique
async function getNextId(counterName) {
    const counterRef = doc(db, "counters", counterName);

    try {
        const newId = await runTransaction(db, async (transaction) => {
            const counterDoc = await transaction.get(counterRef);
            let nextId = 1;

            if (counterDoc.exists()) {
                nextId = counterDoc.data().value + 1;
                transaction.update(counterRef, { value: nextId });
            } else {
                transaction.set(counterRef, { value: nextId });
            }

            return nextId;
        });

        return newId;
    } catch (error) {
        console.error("❌ Erreur lors de la génération de l'ID:", error);
        throw error;
    }
}

// Attendre que le DOM soit chargé avant d'attacher l'événement
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('vente-form');

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Empêcher l'envoi automatique du formulaire

        try {
            const formData = new FormData(form);
            const idCrypto = parseInt(formData.get('idCrypto'));
            const nbCrypto = parseFloat(formData.get('quantite'));
            const idUtilisateur = parseInt(formData.get('idUtilisateur'));

            if (isNaN(idCrypto) || isNaN(nbCrypto) || isNaN(idUtilisateur) || nbCrypto <= 0) {
                throw new Error("❌ Données invalides : Assurez-vous que la quantité est un nombre valide.");
            }

            // Obtenir le cours de la cryptomonnaie sélectionnée
            const selectedOption = form.querySelector(`option[value="${idCrypto}"]`);
            const coursActuel = parseFloat(selectedOption.dataset.cours);

            if (isNaN(coursActuel)) {
                throw new Error("❌ Cours de la cryptomonnaie introuvable.");
            }

            // Obtenir le prochain ID pour la vente
            const idVendre = await getNextId("ventes");
            console.log("📌 Nouvel ID de vente généré:", idVendre);

            // Préparer les données pour Firebase
            const venteData = {
                Id_Cryptomonaie: idCrypto,
                Id_utilisateur: idUtilisateur,
                Id_vendre: idVendre,
                cours: coursActuel,
                daty: serverTimestamp(), // Timestamp géré par Firebase
                nb_crypto: nbCrypto
            };

            // Insérer dans Firebase
            await addDoc(collection(db, "mvt_vendre_crypto"), venteData);
            console.log("✅ Vente enregistrée avec succès!");

            // Soumettre le formulaire après insertion Firebase
            form.submit();
        } catch (error) {
            console.error("❌ Erreur lors de l'enregistrement:", error);
            alert("Erreur: " + error.message);
        }
    });
});
