const baseURL = process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080/api/v1/';

export function request(type, id) {
    return fetch(`${baseURL}waitlist/${type}/${id}`,
        { method: 'POST', headers: { Authorization: localStorage.token ?? '' }, mode: 'cors' });
}