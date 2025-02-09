import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getFirestore, doc, updateDoc, getDoc, setDoc, runTransaction } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-firestore.js";

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

async function validerDepot(idDepot, idUtilisateur, solde) {
    const depotRef = doc(db, "mvt_depot_fond", idDepot);
    const fondUserRef = doc(db, "fond_user", idUtilisateur);

    try {
        await runTransaction(db, async (transaction) => {
            const fondDoc = await transaction.get(fondUserRef);
            let nouveauSolde = parseFloat(solde);

            if (fondDoc.exists()) {
                nouveauSolde += parseFloat(fondDoc.data().solde); // Ajouter l'ancien solde
                transaction.update(fondUserRef, { solde: nouveauSolde });
            } else {
                transaction.set(fondUserRef, { idUtilisateur, solde: nouveauSolde });
            }

            transaction.update(depotRef, { etat: true });
        });

        alert("Dépôt validé avec succès !");
        location.reload();
    } catch (error) {
        console.error("Erreur lors de la validation du dépôt :", error);
        alert("Erreur : " + error.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".btn-valider").forEach(button => {
        button.addEventListener("click", async (event) => {
            event.preventDefault();

            const form = button.closest("form");
            const idDepot = form.querySelector("input[name='idDepot']").value;
            const row = form.closest("tr");
            const solde = row.children[1].textContent.trim();
            const idUtilisateur = row.children[3].textContent.trim();

            await validerDepot(idDepot, idUtilisateur, solde);
        });
    });
});
