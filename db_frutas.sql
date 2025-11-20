-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-11-2025 a las 17:01:03
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_frutas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `DEPT_ID` int(11) NOT NULL,
  `NOMBRE` varchar(100) DEFAULT NULL,
  `DESCRIPCION` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`DEPT_ID`, `NOMBRE`, `DESCRIPCION`) VALUES
(1, 'Ancash', 'Departamento del Perú'),
(2, 'Junin', 'Departamento del Perú'),
(3, 'La Libertad', 'Departamento del Perú'),
(4, 'Piura', 'Departamento del Perú'),
(5, 'Amazonas', 'Departamento del Perú'),
(6, 'Apurímac', 'Departamento del Perú'),
(7, 'Arequipa', 'Departamento del Perú'),
(8, 'Ayacucho', 'Departamento del Perú'),
(9, 'Cajamarca', 'Departamento del Perú'),
(10, 'Callao', 'Provincia Constitucional del Perú'),
(11, 'Cusco', 'Departamento del Perú'),
(12, 'Huancavelica', 'Departamento del Perú'),
(13, 'Huánuco', 'Departamento del Perú'),
(14, 'Ica', 'Departamento del Perú'),
(15, 'Lambayeque', 'Departamento del Perú'),
(16, 'Lima', 'Departamento del Perú'),
(17, 'Loreto', 'Departamento del Perú'),
(18, 'Madre de Dios', 'Departamento del Perú'),
(19, 'Moquegua', 'Departamento del Perú'),
(20, 'Pasco', 'Departamento del Perú'),
(21, 'Puno', 'Departamento del Perú'),
(22, 'San Martín', 'Departamento del Perú'),
(23, 'Tacna', 'Departamento del Perú'),
(24, 'Tumbes', 'Departamento del Perú'),
(25, 'Ucayali', 'Departamento del Perú');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fruta`
--

CREATE TABLE `fruta` (
  `FRUTA_ID` int(11) NOT NULL,
  `NOMBRE` varchar(100) DEFAULT NULL,
  `URL_IMG` varchar(255) DEFAULT NULL,
  `DESCRIPCION` text DEFAULT NULL,
  `DEPT_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `fruta`
--

INSERT INTO `fruta` (`FRUTA_ID`, `NOMBRE`, `URL_IMG`, `DESCRIPCION`, `DEPT_ID`) VALUES
(1, 'Aguaymanto', 'http://192.168.100.176:3020/public/fruta/aguaymanto.jpg', 'Fruta andina y amazónica.', 5),
(2, 'Cacao', 'http://192.168.100.176:3020/public/fruta/cacao.jpg', 'Principal cultivo amazónico.', 5),
(3, 'Cocona', 'http://192.168.100.176:3020/public/fruta/cocona.jpg', 'Ideal para jugos exóticos.', 5),
(4, 'Naranja', 'http://192.168.100.176:3020/public/fruta/naranja.jpg', 'Amplia producción cítrica.', 5),
(5, 'Pitahaya', 'http://192.168.100.176:3020/public/fruta/pitahaya.jpg', 'Fruta de la selva alta.', 5),
(6, 'Palta Hass', 'http://192.168.100.176:3020/public/fruta/paltahass.jpg', 'Gran producción de Palta en el Callejón.', 1),
(7, 'Manzana Delicia', 'http://192.168.100.176:3020/public/fruta/manzanadelicia.jpg', 'Variedad de la sierra andina.', 1),
(8, 'Tuna', 'http://192.168.100.176:3020/public/fruta/tuna.jpg', 'Fruto del cactus andino.', 1),
(9, 'Melocotón', 'http://192.168.100.176:3020/public/fruta/melocoton.jpg', 'Cultivo de la zona templada.', 1),
(10, 'Algarrobina', 'http://192.168.100.176:3020/public/fruta/algarrobina.jpg', 'Derivado del algarrobo costeño.', 1),
(11, 'Chirimoya', 'http://192.168.100.176:3020/public/fruta/chirimoya.jpg', 'Fruta dulce de la sierra.', 6),
(12, 'Capulí', 'http://192.168.100.176:3020/public/fruta/capuli.jpg', 'Cereza andina tradicional.', 6),
(13, 'Sauco', 'http://192.168.100.176:3020/public/fruta/sauco.jpg', 'Bayas usadas en mermeladas.', 6),
(14, 'Granadilla', 'http://192.168.100.176:3020/public/fruta/granadilla.jpg', 'Fruta de cáscara dura.', 6),
(15, 'Durazno', 'http://192.168.100.176:3020/public/fruta/durazno.jpg', 'Cultivo de valles interandinos.', 6),
(16, 'Membrillo', 'http://192.168.100.176:3020/public/fruta/membrillo.jpg', 'Usado en dulces y jaleas.', 7),
(17, 'Higo', 'http://192.168.100.176:3020/public/fruta/higo.jpg', 'Cultivo en zonas costeras y valles.', 7),
(18, 'Pera', 'http://192.168.100.176:3020/public/fruta/pera.jpg', 'Cultivo de valle en zonas frías.', 7),
(19, 'Cebada', 'http://192.168.100.176:3020/public/fruta/cebada.jpg', 'Aunque es cereal, es clave en la región.', 7),
(20, 'Maracuyá', 'http://192.168.100.176:3020/public/fruta/maracuya.jpg', 'Producción en valles interandinos.', 7),
(21, 'Lúcuma', 'http://192.168.100.176:3020/public/fruta/lucuma.jpg', 'Fruta de sabor dulce y seco.', 8),
(22, 'Pacay', 'http://192.168.100.176:3020/public/fruta/pacay.jpg', 'Vaina dulce de la sierra.', 8),
(23, 'Níspero', 'http://192.168.100.176:3020/public/fruta/nispero.jpg', 'Cultivado en laderas.', 8),
(24, 'Kiwicha', 'http://192.168.100.176:3020/public/fruta/kiwicha.jpg', 'Grano andino (se usa como fruta en postres).', 8),
(25, 'Tumbarrojo', 'http://192.168.100.176:3020/public/fruta/tumbarrojo.jpg', 'Fruta de la pasión de la sierra.', 8),
(26, 'Mora', 'http://192.168.100.176:3020/public/fruta/mora.jpg', 'Bayas usadas en jugos.', 9),
(27, 'Tamarindo', 'http://192.168.100.176:3020/public/fruta/tamarindo.jpg', 'Cultivo en zonas cálidas.', 9),
(28, 'Guayaba', 'http://192.168.100.176:3020/public/fruta/guayaba.jpg', 'Fruta tropical aromática.', 9),
(29, 'Limoncillo', 'http://192.168.100.176:3020/public/fruta/limoncillo.jpg', 'Cítrico menor de la región.', 9),
(30, 'Frambuesa', 'http://192.168.100.176:3020/public/fruta/frambuesa.jpg', 'Bayas de la sierra.', 9),
(31, 'Plátano de la Isla', 'http://192.168.100.176:3020/public/fruta/platanoisla.jpg', 'Ingreso y distribución portuaria.', 10),
(32, 'Coco', 'http://192.168.100.176:3020/public/fruta/coco.jpg', 'Gran ingreso de fruta del norte.', 10),
(33, 'Carambola', 'http://192.168.100.176:3020/public/fruta/carambola.jpg', 'Fruta tropical importada.', 10),
(34, 'Guanábana', 'http://192.168.100.176:3020/public/fruta/guanabana.jpg', 'Distribución para Lima.', 10),
(35, 'Mangostán', 'http://192.168.100.176:3020/public/fruta/mangostan.jpg', 'Fruta exótica distribuida.', 10),
(36, 'Uva', 'http://192.168.100.176:3020/public/fruta/uva.jpg', 'Vino y piscos locales.', 11),
(37, 'Naranja Agria', 'http://192.168.100.176:3020/public/fruta/naranjaagria.jpg', 'Cítrico de los valles.', 11),
(38, 'Papaya', 'http://192.168.100.176:3020/public/fruta/papaya.jpg', 'Cultivo en zonas bajas.', 11),
(39, 'Granadilla', 'http://192.168.100.176:3020/public/fruta/granadilla.jpg', 'De los valles andinos.', 11),
(40, 'Cacao Chuncho', 'http://192.168.100.176:3020/public/fruta/cacaochuncho.jpg', 'Variedad nativa y fina.', 11),
(41, 'Quinua', 'http://192.168.100.176:3020/public/fruta/quinua.jpg', 'Considerado un superalimento.', 12),
(42, 'Mashua', 'http://192.168.100.176:3020/public/fruta/mashua.jpg', 'Tubérculo andino dulce.', 12),
(43, 'Oca', 'http://192.168.100.176:3020/public/fruta/oca.jpg', 'Tubérculo con sabor afrutado.', 12),
(44, 'Zarzamora', 'http://192.168.100.176:3020/public/fruta/zarzamora.jpg', 'Bayas silvestres andinas.', 12),
(45, 'Melón', 'http://192.168.100.176:3020/public/fruta/melon.jpg', 'Producción de la cuenca del Mantaro.', 12),
(46, 'Café cereza', 'http://192.168.100.176:3020/public/fruta/cafecereza.jpg', 'Fruto del café, gran producción.', 13),
(47, 'Camu Camu', 'http://192.168.100.176:3020/public/fruta/camucamu.jpg', 'Alto contenido de Vitamina C.', 13),
(48, 'Aguaí', 'http://192.168.100.176:3020/public/fruta/aguai.jpg', 'Fruto comestible amazónico.', 13),
(49, 'Pijuayo', 'http://192.168.100.176:3020/public/fruta/pijuayo.jpg', 'Palmera con fruto rico en aceite.', 13),
(50, 'Caimito', 'http://192.168.100.176:3020/public/fruta/caimito.jpg', 'Fruta de la selva alta.', 13),
(51, 'Pecana', 'http://192.168.100.176:3020/public/fruta/pecana.jpg', 'Nuez con gran producción en Ica.', 14),
(52, 'Fresa', 'http://192.168.100.176:3020/public/fruta/fresa.jpg', 'Cultivo intensivo de alta exportación.', 14),
(53, 'Mandarinita', 'http://192.168.100.176:3020/public/fruta/mandarinita.jpg', 'Variedad de mandarina.', 14),
(54, 'Dátil', 'http://192.168.100.176:3020/public/fruta/datil.jpg', 'Fruto seco cultivado en oasis.', 14),
(55, 'Arándano', 'http://192.168.100.176:3020/public/fruta/arandano.jpg', 'Boom exportador en la costa sur.', 14),
(56, 'Piña', 'http://192.168.100.176:3020/public/fruta/pina.jpg', 'Cultivo en la selva central.', 2),
(57, 'Café', 'http://192.168.100.176:3020/public/fruta/cafe.jpg', 'Fruto principal de la selva central.', 2),
(58, 'Achiote', 'http://192.168.100.176:3020/public/fruta/achiote.jpg', 'Usado como especia y colorante.', 2),
(59, 'Pomarrosa', 'http://192.168.100.176:3020/public/fruta/pomarrosa.jpg', 'Fruta aromática de la selva.', 2),
(60, 'Guaba', 'http://192.168.100.176:3020/public/fruta/guaba.jpg', 'Vaina de la zona tropical.', 2),
(61, 'Mango Kent', 'http://192.168.100.176:3020/public/fruta/mangokent.jpg', 'Variedad de mango de exportación.', 3),
(62, 'Mandarina', 'http://192.168.100.176:3020/public/fruta/mandarina.jpg', 'Gran producción de cítricos.', 3),
(63, 'Limón', 'http://192.168.100.176:3020/public/fruta/limon.jpg', 'Cítrico básico en la costa.', 3),
(64, 'Palta Fuerte', 'http://192.168.100.176:3020/public/fruta/paltafuerte.jpg', 'Variedad de palta costeña.', 3),
(65, 'Caña de Azúcar', 'http://192.168.100.176:3020/public/fruta/cañadeazucar.jpg', 'Aunque es tallo, es clave en la agroindustria.', 3),
(66, 'Pera de la costa', 'http://192.168.100.176:3020/public/fruta/peracosta.jpg', 'Adaptada al clima cálido.', 15),
(67, 'Tumbo', 'http://192.168.100.176:3020/public/fruta/tumbo.jpg', 'Fruta de la pasión de la costa.', 15),
(68, 'Chirimoya de la costa', 'http://192.168.100.176:3020/public/fruta/chirimoyacosta.jpg', 'Adaptada a la zona de Olmos.', 15),
(69, 'Cereza de la costa', 'http://192.168.100.176:3020/public/fruta/cerezacosta.jpg', 'Cultivo en agroindustria.', 15),
(70, 'Zapote', 'http://192.168.100.176:3020/public/fruta/zapote.jpg', 'Fruta tropical grande.', 15),
(71, 'Lima', 'http://192.168.100.176:3020/public/fruta/lima.jpg', 'Cítrico base.', 16),
(72, 'Manzana Israel', 'http://192.168.100.176:3020/public/fruta/manzanisrael.jpg', 'Variedad cultivada cerca de la capital.', 16),
(73, 'Uva Italia', 'http://192.168.100.176:3020/public/fruta/uvaitalia.jpg', 'Variedad de uva de mesa.', 16),
(74, 'Palta Criolla', 'http://192.168.100.176:3020/public/fruta/paltacriolla.jpg', 'Variedad histórica de la zona.', 16),
(75, 'Granada', 'http://192.168.100.176:3020/public/fruta/granada.jpg', 'Fruta roja cultivada en Huaral.', 16),
(76, 'Aguaje', 'http://192.168.100.176:3020/public/fruta/aguaje.jpg', 'Fruto de palmera, alto consumo.', 17),
(77, 'Ungurahui', 'http://192.168.100.176:3020/public/fruta/ungurahui.jpg', 'Fruto oleaginoso de palmera.', 17),
(78, 'Arazá', 'http://192.168.100.176:3020/public/fruta/araza.jpg', 'Fruta amazónica cítrica.', 17),
(79, 'Pona', 'http://192.168.100.176:3020/public/fruta/pona.jpg', 'Fruto de palmera de la selva.', 17),
(80, 'Maracuyá de monte', 'http://192.168.100.176:3020/public/fruta/maracuyamonte.jpg', 'Variedad silvestre.', 17),
(81, 'Castaña Amazónica', 'http://192.168.100.176:3020/public/fruta/castaña.jpg', 'Nuez silvestre de exportación.', 18),
(82, 'Palmito', 'http://192.168.100.176:3020/public/fruta/palmito.jpg', 'Brote de palmera (usado como fruta/vegetal).', 18),
(83, 'Asaí', 'http://192.168.100.176:3020/public/fruta/asai.jpg', 'Baya de la palmera de Asaí.', 18),
(84, 'Sachamango', 'http://192.168.100.176:3020/public/fruta/sachamango.jpg', 'Mango silvestre amazónico.', 18),
(85, 'Masato (Yuca)', 'http://192.168.100.176:3020/public/fruta/masato.jpg', 'Base de bebida fermentada (yuca).', 18),
(86, 'Damásco', 'http://192.168.100.176:3020/public/fruta/damasco.jpg', 'Fruta de la cuenca del río Moquegua.', 19),
(87, 'Ciruela', 'http://192.168.100.176:3020/public/fruta/ciruela.jpg', 'Fruta de carozo de los valles.', 19),
(88, 'Tuna de Moquegua', 'http://192.168.100.176:3020/public/fruta/tuna.jpg', 'Variedad de tuna roja/morada.', 19),
(89, 'Higo de Moquegua', 'http://192.168.100.176:3020/public/fruta/higo.jpg', 'Cultivo tradicional en oasis.', 19),
(90, 'Pera Moqueguana', 'http://192.168.100.176:3020/public/fruta/pera.jpg', 'Variedad de valle.', 19),
(91, 'Granadilla de Pasco', 'http://192.168.100.176:3020/public/fruta/granadilla.jpg', 'Alta producción en la selva central.', 20),
(92, 'Yacón', 'http://192.168.100.176:3020/public/fruta/yacon.jpg', 'Raíz dulce andina.', 20),
(93, 'Naranjilla', 'http://192.168.100.176:3020/public/fruta/naranjilla.jpg', 'Cítrico pequeño de la selva.', 20),
(94, 'Tamarillo', 'http://192.168.100.176:3020/public/fruta/tamarillo.jpg', 'Tomate de árbol.', 20),
(95, 'Quinual', 'http://192.168.100.176:3020/public/fruta/quinual.jpg', 'Fruto del árbol Quinual.', 20),
(96, 'Mango de Piura', 'http://192.168.100.176:3020/public/fruta/mangopiura.jpg', 'Mango de alto rendimiento y exportación.', 4),
(97, 'Uva de mesa', 'http://192.168.100.176:3020/public/fruta/uvamesa.jpg', 'Cultivo intensivo y exportador.', 4),
(98, 'Limón Sutil', 'http://192.168.100.176:3020/public/fruta/limonsutil.jpg', 'El limón más común para el ceviche.', 4),
(99, 'Algarroba', 'http://192.168.100.176:3020/public/fruta/algarroba.jpg', 'Vaina del algarrobo.', 4),
(100, 'Tamarindo de Piura', 'http://192.168.100.176:3020/public/fruta/tamarindo.jpg', 'Fruta tropical de clima seco.', 4),
(101, 'Papayita Andina', 'http://192.168.100.176:3020/public/fruta/papayitaandina.jpg', 'Fruta pequeña de sierra.', 21),
(102, 'Tuna Negra', 'http://192.168.100.176:3020/public/fruta/tunanegra.jpg', 'Variedad de tuna resistente al frío.', 21),
(103, 'Manzana de Puno', 'http://192.168.100.176:3020/public/fruta/manzana.jpg', 'Cultivo en zonas altas.', 21),
(104, 'Durazno Serrano', 'http://192.168.100.176:3020/public/fruta/duraznoserrano.jpg', 'Adaptado al clima frío.', 21),
(105, 'Saúco', 'http://192.168.100.176:3020/public/fruta/sauco.jpg', 'Bayas usadas en la repostería local.', 21),
(106, 'Guanábana', 'http://192.168.100.176:3020/public/fruta/guanabana.jpg', 'Fruta de la selva para postres.', 22),
(107, 'Pijuayo', 'http://192.168.100.176:3020/public/fruta/pijuayo.jpg', 'Palmera importante de la selva.', 22),
(108, 'Macambo', 'http://192.168.100.176:3020/public/fruta/macambo.jpg', 'Primo del cacao, alto en aceite.', 22),
(109, 'Uña de Gato', 'http://192.168.100.176:3020/public/fruta/uñadegato.jpg', 'Corteza y fruto medicinal.', 22),
(110, 'Arazá de San Martín', 'http://192.168.100.176:3020/public/fruta/araza.jpg', 'Fruta de alto valor nutricional.', 22),
(111, 'Durazno Huayco', 'http://192.168.100.176:3020/public/fruta/duraznohuayco.jpg', 'Variedad de durazno local.', 23),
(112, 'Albaricoque', 'http://192.168.100.176:3020/public/fruta/albaricoque.jpg', 'Cultivo en los valles.', 23),
(113, 'Manzana Tacneña', 'http://192.168.100.176:3020/public/fruta/manzana.jpg', 'Variedad adaptada al clima de valle.', 23),
(114, 'Pera Tacneña', 'http://192.168.100.176:3020/public/fruta/pera.jpg', 'Fruta de carozo de la región.', 23),
(115, 'Higo Tacneño', 'http://192.168.100.176:3020/public/fruta/higo.jpg', 'Cultivo tradicional.', 23),
(116, 'Plátano Bellaco', 'http://192.168.100.176:3020/public/fruta/platanobellaco.jpg', 'Tipo de plátano para cocinar.', 24),
(117, 'Coco Tumbesino', 'http://192.168.100.176:3020/public/fruta/cocotumbesino.jpg', 'Fruto de las palmeras costeras.', 24),
(118, 'Grosella', 'http://192.168.100.176:3020/public/fruta/grosella.jpg', 'Fruta tropical silvestre.', 24),
(119, 'Badea', 'http://192.168.100.176:3020/public/fruta/badea.jpg', 'Maracuyá gigante de Tumbes.', 24),
(120, 'Sandía', 'http://192.168.100.176:3020/public/fruta/sandia.jpg', 'Fruta de alto cultivo en el norte.', 24),
(121, 'Sacha Inchi', 'http://192.168.100.176:3020/public/fruta/sachainchi.jpg', 'Nuez oleaginosa amazónica.', 25),
(122, 'Arazá de Ucayali', 'http://192.168.100.176:3020/public/fruta/araza.jpg', 'Fruta cítrica de la selva.', 25),
(123, 'Acerola', 'http://192.168.100.176:3020/public/fruta/acerola.jpg', 'Alto contenido de Vitamina C.', 25),
(124, 'Uva Amazónica', 'http://192.168.100.176:3020/public/fruta/uvaamazonica.jpg', 'Fruta silvestre de la selva.', 25),
(125, 'Pona de Ucayali', 'http://192.168.100.176:3020/public/fruta/pona.jpg', 'Fruto de palmera importante.', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE `log` (
  `LOG_ID` int(11) NOT NULL,
  `SESSION_ID` int(11) DEFAULT NULL,
  `TIPO_LOG_ID` int(11) DEFAULT NULL,
  `FECHA_EVENTO` datetime DEFAULT NULL,
  `FRUTA_ID` int(11) DEFAULT NULL,
  `DEPARTAMENTO_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`LOG_ID`, `SESSION_ID`, `TIPO_LOG_ID`, `FECHA_EVENTO`, `FRUTA_ID`, `DEPARTAMENTO_ID`) VALUES
(1, 1, 3, '2025-11-15 09:01:00', NULL, 1),
(2, 2, 3, '2025-11-15 11:31:00', NULL, 2),
(3, 3, 3, '2025-11-16 10:01:00', NULL, 3),
(4, 4, 3, '2025-11-16 14:21:00', NULL, 4),
(5, 5, 3, '2025-11-17 08:01:00', NULL, 2),
(6, 6, 3, '2025-11-17 16:46:00', NULL, 4),
(7, 7, 3, '2025-11-18 19:01:00', NULL, 3),
(8, 8, 3, '2025-11-18 21:11:00', NULL, 1),
(9, 9, 3, '2025-11-19 10:31:00', NULL, 4),
(10, 2, 3, '2025-11-15 11:35:00', NULL, 4),
(11, 3, 3, '2025-11-16 10:05:00', NULL, 4),
(12, 5, 3, '2025-11-17 08:05:00', NULL, 3),
(13, 1, 3, '2025-11-15 09:05:00', NULL, 2),
(14, 6, 3, '2025-11-17 16:50:00', NULL, 4),
(15, 1, 2, '2025-11-15 09:02:00', NULL, NULL),
(16, 2, 2, '2025-11-15 11:32:00', NULL, NULL),
(17, 3, 2, '2025-11-16 10:02:00', NULL, NULL),
(18, 4, 2, '2025-11-16 14:22:00', NULL, NULL),
(19, 5, 2, '2025-11-17 08:02:00', NULL, NULL),
(20, 6, 2, '2025-11-17 16:47:00', NULL, NULL),
(21, 7, 2, '2025-11-18 19:02:00', NULL, NULL),
(22, 8, 2, '2025-11-18 21:12:00', NULL, NULL),
(23, 9, 2, '2025-11-19 10:32:00', NULL, NULL),
(24, 4, 2, '2025-11-16 14:25:00', NULL, NULL),
(25, 1, 2, '2025-11-15 09:08:00', NULL, NULL),
(26, 1, 3, '2025-11-15 09:01:00', NULL, 1),
(27, 2, 3, '2025-11-15 11:31:00', NULL, 2),
(28, 3, 3, '2025-11-16 10:01:00', NULL, 3),
(29, 4, 3, '2025-11-16 14:21:00', NULL, 4),
(30, 5, 3, '2025-11-17 08:01:00', NULL, 2),
(31, 6, 3, '2025-11-17 16:46:00', NULL, 4),
(32, 7, 3, '2025-11-18 19:01:00', NULL, 3),
(33, 8, 3, '2025-11-18 21:11:00', NULL, 1),
(34, 9, 3, '2025-11-19 10:31:00', NULL, 4),
(35, 2, 3, '2025-11-15 11:35:00', NULL, 4),
(36, 3, 3, '2025-11-16 10:05:00', NULL, 4),
(37, 5, 3, '2025-11-17 08:05:00', NULL, 3),
(38, 1, 3, '2025-11-15 09:05:00', NULL, 2),
(39, 6, 3, '2025-11-17 16:50:00', NULL, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `session`
--

CREATE TABLE `session` (
  `SESSION_ID` int(11) NOT NULL,
  `USUARIO_ID` int(11) DEFAULT NULL,
  `DEPTO_ID_INICIAL` int(11) DEFAULT NULL,
  `FECHA` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `session`
--

INSERT INTO `session` (`SESSION_ID`, `USUARIO_ID`, `DEPTO_ID_INICIAL`, `FECHA`) VALUES
(1, 1, 1, '2025-11-15 09:00:00'),
(2, 2, 2, '2025-11-15 11:30:00'),
(3, 3, 3, '2025-11-16 10:00:00'),
(4, 1, 4, '2025-11-16 14:20:00'),
(5, 2, 2, '2025-11-17 08:00:00'),
(6, 3, 4, '2025-11-17 16:45:00'),
(7, 1, 3, '2025-11-18 19:00:00'),
(8, 2, 1, '2025-11-18 21:10:00'),
(9, 3, 2, '2025-11-19 10:30:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_log`
--

CREATE TABLE `tipo_log` (
  `ID_TIPO_LOG` int(11) NOT NULL,
  `NOMBRE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo_log`
--

INSERT INTO `tipo_log` (`ID_TIPO_LOG`, `NOMBRE`) VALUES
(1, 'Ingreso'),
(2, 'Consulta fruta'),
(3, 'Cambio departamento'),
(4, 'Salida');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tokens`
--

CREATE TABLE `tokens` (
  `id` varchar(255) NOT NULL,
  `input` int(11) DEFAULT NULL,
  `output` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tokens`
--

INSERT INTO `tokens` (`id`, `input`, `output`, `total`, `created_at`) VALUES
('chatcmpl-9aBcB123defGHIjklmno', 1305, 221, 1526, '2024-05-24 10:00:00'),
('chatcmpl-9aBCd123efgHIJklmno', 1250, 210, 1460, '2024-05-20 10:15:30'),
('chatcmpl-9aBeD456ghiJKLmnopqr', 1275, 213, 1488, '2024-05-24 13:40:10'),
('chatcmpl-9aBEf456ghiJKLmnopqr', 1280, 215, 1495, '2024-05-20 14:22:05'),
('chatcmpl-9aBgF789ijkLMNopqrs', 1265, 212, 1477, '2024-05-24 15:25:40'),
('chatcmpl-9aBHg789ijkLMNopqrs', 1245, 208, 1453, '2024-05-20 18:45:10'),
('chatcmpl-9aBiH012mnoPQRstuvw', 1290, 217, 1507, '2024-05-25 11:11:11'),
('chatcmpl-9aBkI345nopQRSstuvwx', 1315, 222, 1537, '2024-05-25 14:30:00'),
('chatcmpl-9aBKl012mnoPQRstuvw', 1310, 220, 1530, '2024-05-21 09:05:00'),
('chatcmpl-9aBNm345nopQRSstuvwx', 1260, 212, 1472, '2024-05-21 11:30:45'),
('chatcmpl-9aBPq678pqrSTUvwxyza', 1295, 218, 1513, '2024-05-22 16:00:15'),
('chatcmpl-9aBSr901stuVWXyzabcd', 1270, 214, 1484, '2024-05-22 17:33:20'),
('chatcmpl-9aBVt234vwxYZAbcdefg', 1300, 219, 1519, '2024-05-23 08:55:55'),
('chatcmpl-9aBXw567yzaBCDefghij', 1255, 211, 1466, '2024-05-23 12:10:30'),
('chatcmpl-9aBZy890bcdEFGhijklm', 1285, 216, 1501, '2024-05-23 20:05:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `USUARIO_ID` int(11) NOT NULL,
  `NOMBRE` varchar(150) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PASSW` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`USUARIO_ID`, `NOMBRE`, `EMAIL`, `PASSW`) VALUES
(1, 'Ana Pérez', 'ana.perez@example.com', 'pass123'),
(2, 'Luis Gómez', 'luis.gomez@example.com', 'secure456'),
(3, 'María Torres', 'maria.torres@example.com', 'clave789');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`DEPT_ID`);

--
-- Indices de la tabla `fruta`
--
ALTER TABLE `fruta`
  ADD PRIMARY KEY (`FRUTA_ID`),
  ADD KEY `DEPT_ID` (`DEPT_ID`);

--
-- Indices de la tabla `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`LOG_ID`),
  ADD KEY `SESSION_ID` (`SESSION_ID`),
  ADD KEY `TIPO_LOG_ID` (`TIPO_LOG_ID`),
  ADD KEY `FRUTA_ID` (`FRUTA_ID`),
  ADD KEY `DEPARTAMENTO_ID` (`DEPARTAMENTO_ID`);

--
-- Indices de la tabla `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`SESSION_ID`),
  ADD KEY `USUARIO_ID` (`USUARIO_ID`),
  ADD KEY `DEPTO_ID_INICIAL` (`DEPTO_ID_INICIAL`);

--
-- Indices de la tabla `tipo_log`
--
ALTER TABLE `tipo_log`
  ADD PRIMARY KEY (`ID_TIPO_LOG`);

--
-- Indices de la tabla `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`USUARIO_ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `DEPT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `fruta`
--
ALTER TABLE `fruta`
  MODIFY `FRUTA_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;

--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
  MODIFY `LOG_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT de la tabla `session`
--
ALTER TABLE `session`
  MODIFY `SESSION_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `tipo_log`
--
ALTER TABLE `tipo_log`
  MODIFY `ID_TIPO_LOG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `USUARIO_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `fruta`
--
ALTER TABLE `fruta`
  ADD CONSTRAINT `fruta_ibfk_1` FOREIGN KEY (`DEPT_ID`) REFERENCES `departamento` (`DEPT_ID`);

--
-- Filtros para la tabla `log`
--
ALTER TABLE `log`
  ADD CONSTRAINT `log_ibfk_1` FOREIGN KEY (`SESSION_ID`) REFERENCES `session` (`SESSION_ID`),
  ADD CONSTRAINT `log_ibfk_2` FOREIGN KEY (`TIPO_LOG_ID`) REFERENCES `tipo_log` (`ID_TIPO_LOG`),
  ADD CONSTRAINT `log_ibfk_3` FOREIGN KEY (`FRUTA_ID`) REFERENCES `fruta` (`FRUTA_ID`),
  ADD CONSTRAINT `log_ibfk_4` FOREIGN KEY (`DEPARTAMENTO_ID`) REFERENCES `departamento` (`DEPT_ID`);

--
-- Filtros para la tabla `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`USUARIO_ID`),
  ADD CONSTRAINT `session_ibfk_2` FOREIGN KEY (`DEPTO_ID_INICIAL`) REFERENCES `departamento` (`DEPT_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
