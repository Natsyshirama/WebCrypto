// Importer Firebase
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getFirestore, collection, addDoc, doc, getDoc, updateDoc, runTransaction, serverTimestamp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-firestore.js";

// Configuration Firebase
const firebaseConfig = {
    apiKey: "AIzaSyDwfQNfgWkGWpd0Fjib_VHRXuk75m51zds",
    authDomain: "cryptos-d4f6b.firebaseapp.com",
    projectId: "cryptos-d4f6b",
    storageBucket: "cryptos-d4f6b.firebasestorage.app",
    messagingSenderId: "938286766542",
    appId: "1:938286766542:web:6240cb83b29ecd697cb39f",
    measurementId: "G-QHQLP3ZNDY"
};

// Initialisation Firebase
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// Fonction pour obtenir le prochain ID
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
}document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('register-form');
    
    form.addEventListener('submit', async (event) => {
        
        try {
            const formData = new FormData(form);
            
            // Vérification des mots de passe
            const password = formData.get('password');
            const confirmPassword = formData.get('confirmPassword');

            // Obtenir le prochain ID utilisateur
            const userId = await getNextId("users");
            console.log("Nouvel ID utilisateur généré:", userId);

            const userData = {
                id: userId,
                name: formData.get('username'),
                email: formData.get('email'),
                password: formData.get('password'),
                role: 'admin',
                is_verified: false,
                created_at: serverTimestamp(),
                updated_at: serverTimestamp()
            };
            
            try {
                // Insertion dans Firebase
                await addDoc(collection(db, "users"), userData);
                console.log("✅ Utilisateur ajouté dans Firebase avec l'ID:", userId);
                
                // Une fois l'insertion Firebase réussie, soumettre le formulaire
                form.submit();
            } catch (firebaseError) {
                console.error("Erreur Firebase:", firebaseError);
                throw firebaseError;
            }
            
        } catch (error) {
            console.error("❌ Erreur lors de l'inscription:", error);
            alert("Erreur lors de l'inscription. Veuillez réessayer.");
        }
    });
});