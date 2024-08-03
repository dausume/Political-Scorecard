// src/App.tsx
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useAuth } from 'react-oidc-context';
import HomeComponent from './components/homeComponent';
import LoginPage from './components/Login';
import Callback from './components/CallbackComponent'; // Create this component

const App = () => {
  const auth = useAuth();

  if (auth.isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/auth/callback" element={<Callback />} />
        <Route path="/" element={auth.isAuthenticated ? <HomeComponent /> : <LoginPage />} />
      </Routes>
    </Router>
  );
};

export default App;