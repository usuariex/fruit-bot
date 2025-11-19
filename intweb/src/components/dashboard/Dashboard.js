import React, { useState, useEffect } from 'react';
import { Bar, Pie, Line } from 'react-chartjs-2'; // Cambiamos Doughnut por Pie
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement,
} from 'chart.js';
import './Dashboard.css';

// Registrar los componentes necesarios de Chart.js
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement
);

const API_URL = 'http://localhost:3020/api/analytics';

function Dashboard() {
  const [topFrutasData, setTopFrutasData] = useState(null);
  const [logDistributionData, setLogDistributionData] = useState(null); // Nuevo estado para el nuevo gráfico
  const [actividadSesionesData, setActividadSesionesData] = useState(null);
  const [tokenUsageData, setTokenUsageData] = useState(null); // Estado para el consumo de tokens
  const [tokenSummary, setTokenSummary] = useState({ total_input: 0, total_output: 0 }); // Estado para los contadores

  // Envolvemos la lógica de fetching en una función que podamos llamar cuando queramos
  const fetchAllData = () => {
    console.log("Actualizando datos del dashboard...");
    // Función interna para obtener datos de un endpoint
    const fetchData = async (endpoint, setData) => {
        try {
            // Añadimos un parámetro 'cacheBuster' con la fecha actual para evitar el caché del navegador.
            const cacheBuster = new Date().getTime();
            const response = await fetch(`${API_URL}/${endpoint}?t=${cacheBuster}`);
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

            // Lógica especial para el resumen de tokens (contadores)
            if (endpoint === 'token-summary') {
                const summaryData = await response.json();
                // Si los valores son null (porque no hay datos), los convertimos a 0.
                setTokenSummary({
                    total_input: summaryData.total_input || 0,
                    total_output: summaryData.total_output || 0,
                });
                return; // Terminamos aquí para este endpoint
            }

            const data = await response.json();

            // Lógica especial para el gráfico de consumo de tokens
            if (endpoint === 'token-usage') {
                const labels = data.map(item => new Date(item.dia).toLocaleDateString('es-ES', { timeZone: 'UTC' }));
                setData({
                    labels,
                    datasets: [
                        {
                            label: 'Tokens de Entrada',
                            data: data.map(item => item.total_input),
                            borderColor: '#3F51B5', // Azul
                            pointBackgroundColor: '#3F51B5',
                            tension: 0.3,
                            fill: false,
                        },
                        {
                            label: 'Tokens de Salida',
                            data: data.map(item => item.total_output),
                            borderColor: '#4CAF50', // Verde
                            pointBackgroundColor: '#4CAF50',
                            tension: 0.3,
                            fill: false,
                        }
                    ]
                });
            } else {
                // Lógica existente para los otros gráficos
                const labels = data.map(item => item.nombre || new Date(item.dia).toLocaleDateString('es-ES', { timeZone: 'UTC' }));
                const values = data.map(item => item.vistas || item.sesiones);

                const tropicalColors = ['#FF8A65', '#FFD54F', '#4DB6AC', '#7986CB', '#A1887F', '#90A4AE', '#AED581'];

                const isLineChart = endpoint === 'actividad-sesiones';
                const lineChartProps = {
                    borderColor: '#E65100',
                    tension: 0.3,
                    pointBackgroundColor: '#E65100',
                    pointRadius: 5,
                };

                setData({
                    labels,
                    datasets: [{
                        label: 'Visitas',
                        data: values,
                        backgroundColor: tropicalColors,
                        borderWidth: 1,
                        fill: false,
                        ...(isLineChart && lineChartProps)
                    }],
                });
            }
        } catch (error) {
            console.error(`Error fetching ${endpoint}:`, error);
            setData(null); // Limpia los datos en caso de error para que se muestre "Cargando..."
        }
    };

    fetchData('top-frutas', setTopFrutasData);
    fetchData('log-distribution', setLogDistributionData); // Llamamos al nuevo endpoint
    fetchData('actividad-sesiones', setActividadSesionesData);
    fetchData('token-usage', setTokenUsageData); // Llamamos al nuevo endpoint de tokens
    fetchData('token-summary', setTokenSummary); // Llamamos al nuevo endpoint de resumen
  };

  useEffect(() => {
    fetchAllData(); // Carga los datos la primera vez
  }, []);

  return (
    <main className="dashboard-container">
      <div className="dashboard-header">
        <h1>Panel de Analítica de Usuarios</h1>
        <button onClick={fetchAllData} className="refresh-button">Actualizar</button>
      </div>
      <div className="summary-grid">
        <div className="summary-card">
          <h3>Tokens de Entrada (Mes)</h3>
          <p>{tokenSummary.total_input.toLocaleString('es-ES')}</p>
        </div>
        <div className="summary-card">
          <h3>Tokens de Salida (Mes)</h3>
          <p>{tokenSummary.total_output.toLocaleString('es-ES')}</p>
        </div>
      </div>
      <div className="charts-grid">
        <div className="chart-card">
          <h2>Top 10 Frutas Más Vistas</h2>
          {topFrutasData ? <Bar data={topFrutasData} options={{ responsive: true, indexAxis: 'y' }} /> : <p>Cargando datos...</p>}
        </div>
        <div className="chart-card">
          <h2>Resumen de Actividad</h2>
          {logDistributionData ? (
            logDistributionData.labels.length > 0 ? (
              <Pie data={logDistributionData} options={{ responsive: true }} />
            ) : (
              <p className="no-data-message">No hay datos de actividad para mostrar.</p>
            )
          ) : <p>Cargando datos...</p>}
        </div>
        <div className="chart-card full-width">
          <h2>Actividad de Sesiones por Día</h2>
          {actividadSesionesData ? <Line data={actividadSesionesData} options={{ responsive: true }} /> : <p>Cargando datos...</p>}
        </div>
        <div className="chart-card full-width">
          <h2>Consumo de Tokens por Día (Mes Actual)</h2>
          {tokenUsageData ? (
            tokenUsageData.labels.length > 0 ? (
              <Line data={tokenUsageData} options={{ responsive: true }} />
            ) : (
              <p className="no-data-message">No hay datos de consumo de tokens para mostrar.</p>
            )
          ) : <p>Cargando datos...</p>}
        </div>
      </div>
    </main>
  );
}

export default Dashboard;