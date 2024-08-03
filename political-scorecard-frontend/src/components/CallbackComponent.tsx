// src/Callback.tsx
import React, { useEffect } from 'react';
import { useAuth } from 'react-oidc-context';
import { useNavigate } from 'react-router-dom';

const Callback = () => {
  const auth = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    auth.signinRedirect().then(() => {
      navigate('/');
    }).catch((error) => {
      console.error('Error handling callback', error);
    });
  }, [auth, navigate]);

  return <div>Loading...</div>;
};

export default Callback;