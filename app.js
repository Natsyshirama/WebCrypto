document.addEventListener('DOMContentLoaded', () => {
    let chartInstance = null;
    let cryptoData = [];
    let balance = 10000.00; // Solde initial

    // Initialisation du graphique
    function initChart(labels = [], data = []) {
        const ctx = document.getElementById('crypto-chart').getContext('2d');

        if (chartInstance) {
            chartInstance.destroy();
        }

        chartInstance = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Évolution du cours',
                    borderColor: '#4bc0c0',
                    data: data,
                    fill: false,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        title: {
                            display: true,
                            text: 'Prix (€)'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Date'
                        }
                    }
                }
            }
        });
    }

    // Récupération de la liste des cryptos
    function fetchCryptoList() {
        fetch('/api/cryptoList')
            .then(response => response.json())
            .then(data => {
                cryptoData = data;
                renderCryptoList(data);
            })
            .catch(error => console.error('Erreur:', error));
    }

    // Affichage de la liste des cryptos
    function renderCryptoList(cryptos) {
        const container = document.getElementById('crypto-list-container');
        container.innerHTML = '';

        const list = document.createElement('div');
        list.id = 'crypto-list';

        cryptos.forEach(crypto => {
            const item = document.createElement('div');
            item.className = 'crypto-item';
            item.dataset.id = crypto.id;
            item.innerHTML = `
                <i class="fas fa-coins"></i>
                ${crypto.nom} (${crypto.symbole})
                <span class="price-indicator">Chargement...</span>
            `;
            list.appendChild(item);
        });

        container.appendChild(list);
        addCryptoItemListeners();
        startPriceUpdates();
    }

    // Gestion des clics sur les cryptos
    function addCryptoItemListeners() {
        document.querySelectorAll('.crypto-item').forEach(item => {
            item.addEventListener('click', async(e) => {
                const cryptoId = e.currentTarget.dataset.id;
                const selectedCrypto = cryptoData.find(c => c.id == cryptoId);

                // Mise à jour du graphique
                const historicData = await fetchHistoricData(cryptoId);
                updateChart(historicData, selectedCrypto);

                // Mise en surbrillance de la sélection
                document.querySelectorAll('.crypto-item').forEach(i =>
                    i.classList.remove('selected'));
                e.currentTarget.classList.add('selected');
            });
        });
    }

    // Récupération des données historiques
    async function fetchHistoricData(cryptoId) {
        try {
            const response = await fetch(`/api/coursHistorique?idCrypto=${cryptoId}`);
            return await response.json();
        } catch (error) {
            console.error('Erreur:', error);
            return [];
        }
    }

    // Mise à jour du graphique
    function updateChart(data, crypto) {
        const labels = data.map(entry =>
            new Date(entry.datyUpdate).toLocaleString('fr-FR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            })
        );

        const values = data.map(entry => entry.cours);

        initChart(labels, values);
        chartInstance.data.datasets[0].label = `${crypto.nom} (${crypto.symbole})`;
        chartInstance.update();
    }

    // Mise à jour en temps réel des prix
    function startPriceUpdates() {
        setInterval(() => {
            document.querySelectorAll('.crypto-item').forEach(async(item) => {
                const cryptoId = item.dataset.id;
                const priceElement = item.querySelector('.price-indicator');

                try {
                    const response = await fetch(`/api/coursHistorique?idCrypto=${cryptoId}&limit=1`);
                    const data = await response.json();
                    priceElement.textContent = `€${data[0]?.cours?.toFixed(2) || 'N/A'}`;
                } catch (error) {
                    priceElement.textContent = 'Erreur';
                }
            });
        }, 30000); // Actualisation toutes les 30 secondes
    }

    // Initialisation
    fetchCryptoList();
    initChart();
    updateBalance();

    function updateBalance() {
        document.getElementById('balance').textContent = balance.toFixed(2);
    }
});