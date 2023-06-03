const baseURL = process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080/api/v1/';

export function signin(username, password) {
    return account(username, password, 'GET');
}

export function signup(username, password) {
    return account(username, password, 'POST');
}

async function account(username, password, method) {
    const response = await fetch(
        `${baseURL}member/${encodeURIComponent(username)}/${encodeURIComponent(password)}`,
        { method, mode: 'cors' });
    if (response.ok) {
        localStorage.token = await response.text();
    } else {
        throw response.status;
    }
}