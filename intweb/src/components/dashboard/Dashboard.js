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

  // Envolvemos la lógica de fetching en una función que podamos llamar cuando queramos
  const fetchAllData = () => {
    console.log("Actualizando datos del dashboard...");
    // Función interna para obtener datos de un endpoint
    const fetchData = async (endpoint, setData) => {
        try {
            const response = await fetch(`${API_URL}/${endpoint}`);
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            const data = await response.json();
            
            const labels = data.map(item => item.nombre || new Date(item.dia).toLocaleDateString('es-ES', { timeZone: 'UTC' }));
            const values = data.map(item => item.vistas || item.sesiones);

            // Paleta de colores tropical
            const tropicalColors = [
                '#FF6384', // Sandía
                '#FFCE56', // Mango
                '#4BC0C0', // Menta
                '#9966FF', // Maracuyá
                '#FF9F40', // Papaya
                '#36A2EB', // Arándano
                '#8AC926', // Kiwi
            ];

            // Propiedades específicas para el gráfico de línea
            const isLineChart = endpoint === 'actividad-sesiones';
            const lineChartProps = {
                borderColor: '#E65100', // Color papaya oscuro para la línea
                tension: 0.3, // Suaviza la línea
                pointBackgroundColor: '#E65100',
                pointRadius: 5,
            };

            setData({
                labels,
                datasets: [{
                    label: 'Visitas', // Etiqueta genérica
                    data: values,
                    backgroundColor: tropicalColors,
                    borderWidth: 1,
                    fill: false,
                    ...(isLineChart && lineChartProps) // Aplica props de línea solo si es el gráfico correcto
                }],
            });
        } catch (error) {
            console.error(`Error fetching ${endpoint}:`, error);
            setData(null); // Limpia los datos en caso de error para que se muestre "Cargando..."
        }
    };

    fetchData('top-frutas', setTopFrutasData);
    fetchData('log-distribution', setLogDistributionData); // Llamamos al nuevo endpoint
    fetchData('actividad-sesiones', setActividadSesionesData);
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
      </div>
    </main>
  );
}

export default Dashboard;