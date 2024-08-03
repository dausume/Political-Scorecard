// src/oidcSettings.ts
import { keycloakConfig } from "./keycloakConfig";
import { WebStorageStateStore } from "oidc-client-ts";

export const oidcSettings = {
  authority: `${keycloakConfig.authorityUrl}/auth/realms/${keycloakConfig.realm}`,
  client_id: keycloakConfig.clientId,
  //client_secret: keycloakConfig.clientSecret,
  redirect_uri: `${window.location.origin}/auth/callback`,
  silent_redirect_uri: `${window.location.origin}/auth/silent-refresh`,
  post_logout_redirect_uri: `${window.location.origin}`,
  response_type: "code",
  scope: "openid profile",
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  loadUserInfo: true,
};
