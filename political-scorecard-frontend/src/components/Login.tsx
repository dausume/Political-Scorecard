// src/LoginPage.tsx
import React, { useCallback } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from 'react-oidc-context';

const LoginPage = () => {
  const location = useLocation();
  const auth = useAuth();
  const currentLocationState = location.state || {
    from: { pathname: '/' },
  };

  const login = useCallback(() => {
    auth.signinRedirect();
  }, [auth]);

  if (auth.isAuthenticated) {
    return <Navigate to={currentLocationState.from.pathname || '/'} />;
  }

  return (
    <div>
      <button type="button" onClick={login}>
        Login
      </button>
    </div>
  );
};

export default LoginPage;