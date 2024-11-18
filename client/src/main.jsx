import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './styles/index.css'
import Login from './pages/Login.jsx'
import Test from './pages/Test';

createRoot(document.getElementById('root')).render(
	<StrictMode>
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Login />} />
				<Route path="/test" element={<Test />} />
			</Routes>
		</BrowserRouter>
	</StrictMode>
)
