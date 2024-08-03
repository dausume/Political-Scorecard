// src/index.tsx
import React from 'react';
import { createRoot } from 'react-dom/client';
import { AuthProvider } from "react-oidc-context";
import App from './App';
import { oidcSettings } from './oidcSettings';

const domNode = document.getElementById('root') as HTMLElement;
const root = createRoot(domNode);

root.render(
  <AuthProvider {...oidcSettings}>
    <App />
  </AuthProvider>
);
