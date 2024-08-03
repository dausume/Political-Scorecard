// src/components/homeComponent.tsx
import React from 'react';
import { useAuth } from 'react-oidc-context';

const HomeComponent = () => {
  const auth = useAuth();

  return (
    <div>
      <h1>Welcome, {auth.user?.profile?.preferred_username}!</h1>
      <button onClick={() => auth.signoutRedirect()}>Logout</button>
    </div>
  );
};

export default HomeComponent;