import SignInOrUp from '../services/LoginService';

export default function Login({ isShown, setLoginShown }) {
    // warns the user that the credentials are invalid.
    // this should probably be changed.
    const warnInvalidCredentials = alert;

    return isShown && (
        <div className="login-backdrop" onClick={backdropClick}>
            <div className="login-box" role="dialog">
                <button className="close-login btn close"
                    aria-label="Close"
                    onClick={() => setLoginShown(false)}>
                    &times;
                </button>
                <p></p>
                <p>
                    <label>
                        Username:
                        <input className="form-control" placeholder="Enter username"/>
                    </label>
                </p>
                <p>
                    <label>
                        Password:
                        <input
                            type="password"
                            className="form-control"
                            placeholder="Enter password"/>
                    </label>
                </p>
                <p>
                    <button
                        className="btn btn-primary"
                        onClick = { event => handleClick(event, 'GET') }>
                        Sign in
                    </button>
                    <button
                        className="btn btn-success"
                        onClick = { event => handleClick(event, 'POST') }>
                        Create account
                    </button>
                </p>
            </div>
        </div>
    );

    // handle a click on the sign in or create account buttons
    async function handleClick({ target }, method) {
        let [{ value: username }, { value: password }] =
            target.parentElement.parentElement.querySelectorAll('input');
        username = username.trim();
        if (username === '' || password === '') {
            warnInvalidCredentials('Username or password cannot be empty.');
        } else {
            try {
                await SignInOrUp(username, password, method);
                setLoginShown(false);
            } catch (error) {
                if (error === 401) {
                    warnInvalidCredentials('Incorrect username or password.');
                } else if (error === 409) {
                    warnInvalidCredentials('Username already taken.');
                } else {
                    console.error(error);
                }
            }
        }
    }

    // hide the login when the user clicks on the backdrop
    function backdropClick({ target }) {
        if (target.classList.contains('login-backdrop')) {
            setLoginShown(false);
        }
    }
}