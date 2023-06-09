const baseURL = process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080/api/v1/';

export default async function SignInOrUp(username, password, method) {
    const response = await fetch(
        `${baseURL}member/${encodeURIComponent(username)}/${encodeURIComponent(password)}`,
        { method, mode: 'cors', headers: { Authorization: localStorage.token ?? '' } });
    if (response.ok) {
        localStorage.token = await response.text();
    } else {
        throw response.status;
    }
}