// Importation des modules Firebase
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getFirestore, collection, addDoc, doc, runTransaction, serverTimestamp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-firestore.js";

const firebaseConfig = {
    apiKey: "AIzaSyDwfQNfgWkGWpd0Fjib_VHRXuk75m51zds",
    authDomain: "cryptos-d4f6b.firebaseapp.com",
    projectId: "cryptos-d4f6b",
    storageBucket: "cryptos-d4f6b.appspot.com",
    messagingSenderId: "938286766542",
    appId: "1:938286766542:web:6240cb83b29ecd697cb39f",
    measurementId: "G-QHQLP3ZNDY"
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

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

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('achat-form');

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); 

        try {
            const formData = new FormData(form);
            const idCrypto = parseInt(formData.get('idCrypto'));
            const nbCrypto = parseFloat(formData.get('quantite'));
            const idUtilisateur = parseInt(formData.get('idUtilisateur'));

            if (isNaN(idCrypto) || isNaN(nbCrypto) || isNaN(idUtilisateur) || nbCrypto <= 0) {
                console.log(formData.get('idCrypto'), formData.get('quantite'), formData.get('idUtilisateur'));
                throw new Error("❌ Données invalides : Assurez-vous que la quantité est un nombre valide.");
            }

          
            const selectedOption = form.querySelector(`option[value="${idCrypto}"]`);
            if (!selectedOption) {
                throw new Error("❌ Cryptomonnaie sélectionnée introuvable.");
            }
            const coursActuel = parseFloat(selectedOption.dataset.cours);

            if (isNaN(coursActuel)) {
                throw new Error("❌ Cours de la cryptomonnaie introuvable.");
            }

            const idAchat = await getNextId("achats");
            console.log("📌 Nouvel ID d'achat généré:", idAchat);

            const achatData = {
                Id_Cryptomonaie: idCrypto,
                Id_utilisateur: idUtilisateur,
                Id_achat: idAchat,
                cours: coursActuel,
                daty: serverTimestamp(), 
                nb_crypto: nbCrypto
            };

           
            await addDoc(collection(db, "mvt_achat_crypto"), achatData);
            console.log("✅ Achat enregistré avec succès!");

            
            window.location.href = "achatCrypto";
        } catch (error) {
            console.error("❌ Erreur lors de l'achat:", error);
            alert("Erreur: " + error.message);
        }
    });
});
