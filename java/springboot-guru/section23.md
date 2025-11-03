# Spring Authorization Server

- setup auth server + resource server

## OAuth2

- Authorization framework
- grant limited access to resources without full access to account
- eg: getting user email, name from google account
- roles:
  - resource owner: user who wishes to grant the client access to a resource (you)
  - client: application trying to get access (my project)
  - resource server: resource to access (google account details)
  - authorization server: verifies the identity of user, issues access tokens to application (google auth server)
- different oauth auth flows:
  - authorization code flow
  - client credientials flow
  - resource owner password flow
  - implicit flow
  - hybrid flow
  - device authorization flow
  - authorization code flow with PKCE

## Focus on Client Credentials Flow w/JWT

- client requests auth from user
- user grants auth
- with the grant, client checks auth server
- auth server gives access token
- with access token, client tries to access resource
- resource server validates token
- if valid, sends access to resource to client

## JWT

- json web tokens
- user info + authorized roles
- 3 parts:
  - header
  - payload
  - signature
- base64 encoded
- jwts are signed, so client's can't alter the token
- signing is done by:
  - symmetric encryption: single key to sign, but key needs to be shared
  - asymmetric encryption: private key to sign (not shared), public key to verify
- auth server signs using private key,
- resource server requests public key and verifies to token

## HTTP basic vs Oauth 2

- shared with every resource vs only auth server
- no roles vs role scoped

## Authorization server

- spring authorization server setup
- dependencies: spring security, spring security oauth2 auth server

### Authorization filter chain

- First filter in the security filter chain.
- Override the default configuration by adding `@Configuration`
- authorizaitonServerSecurityFilterChain(HttpSecurity http)

### Default security filter chain

- defaultSecurityFilterChain(HttpSecurity http)
- by default run this. secure everything else not auth by oauth defined above.

### UserDetailsService

- create an instance of user through many ways.
  - in memory user details service (won't be utilized in client credientials auth flow)

### Registered Client Repository

- regiter clients with client id, secrets, etc. {noop} -> to indicate store in plain text in db
- set client id, secret, authorization types, redirect, scopes,

### JWK Source

- signing jwt token using RSA (jwkSource())
- generateRsaKey()
- create JWKSet(rsaKey) and return the jwt token
- public key for validation, private key for signing

### JWT Decoder

- decoder interface

### AuthorizationServerSettings

- where different endpoints for revoking, setting, up, etc.
