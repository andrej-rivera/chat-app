import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";

import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";

// import pages
import App from "./App";
import TestPage from "./pages/TestPage";
import LoginPage from "./pages/LoginPage";
import HeroPage from "./pages/HeroPage";
import DashboardPage from "./pages/DashboardPage";

// create router for all pages — login is a top-level (non-nested) route
const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<App />}>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/hero" element={<HeroPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="*" element={<TestPage />} />
      </Route>
    </>
  )
);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
