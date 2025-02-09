import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getFirestore, collection, addDoc, doc, runTransaction, serverTimestamp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-firestore.js";

const firebaseConfig = {
    apiKey: "AIzaSyDwfQNfgWkGWpd0Fjib_VHRXuk75m51zds",
    authDomain: "cryptos-d4f6b.firebaseapp.com",
    projectId: "cryptos-d4f6b",
    storageBucket: "cryptos-d4f6b.firebasestorage.app",
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
        console.error("Erreur lors de la génération de l'ID:", error);
        throw error;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('retrait-form');
    
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); 

        try {
            const formData = new FormData(form);
            const montant = parseFloat(formData.get('solde'));
            const idUtilisateur = parseInt(formData.get('idUtilisateur'));

            if (isNaN(montant) || isNaN(idUtilisateur)) {
                throw new Error("Montant ou ID utilisateur invalide.");
            }

            const retraitId = await getNextId("retraits");
            console.log("Nouvel ID de retrait généré:", retraitId);

            const retraitData = {
                id: retraitId,
                solde: montant,
                idUtilisateur: idUtilisateur,
                created_at: serverTimestamp(),
                updated_at: serverTimestamp(),
                type: "retrait"
            };

            await addDoc(collection(db, "mvt_retrait_fond"), retraitData);
            console.log("✅ Retrait enregistré dans Firebase avec succès!");
            
            form.submit();
        } catch (error) {
            console.error("❌ Erreur lors de l'enregistrement:", error);
            alert("Erreur lors de l'enregistrement: " + error.message);
        }
    });
});
