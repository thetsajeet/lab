const statusDisplay = document.getElementById("status-display");
const responseLog = document.getElementById("response-log");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");

function log(data) {
  const timestamp = new Date().toLocaleTimeString();
  responseLog.textContent = `[${timestamp}]\n${JSON.stringify(data, null, 2)}`;

  // Update status if user info is present
  if (data.user) {
    statusDisplay.textContent = `Logged in as: ${data.user}`;
    statusDisplay.style.color = "#22c55e";
  } else if (
    data.message &&
    (data.message.includes("Logout") || data.message.includes("Unauthorized"))
  ) {
    statusDisplay.textContent = "Not logged in";
    statusDisplay.style.color = "#94a3b8";
  }
}

async function fetchEndpoint(url, method = "GET", body = null) {
  try {
    const options = {
      method,
      headers: {
        "Content-Type": "application/json",
      },
    };
    if (body) {
      options.body = JSON.stringify(body);
    }

    const response = await fetch(url, options);
    const data = await response.json();

    // Add status code to the log data for visibility
    data._status = response.status;

    log(data);
  } catch (error) {
    log({ error: error.message });
  }
}

document.getElementById("btn-unprotected").addEventListener("click", () => {
  fetchEndpoint("/unprotected");
});

document.getElementById("btn-protected").addEventListener("click", () => {
  fetchEndpoint("/protected");
});

document.getElementById("btn-login").addEventListener("click", () => {
  const username = usernameInput.value;
  const password = passwordInput.value;
  fetchEndpoint("/login", "POST", { username, password });
});

document.getElementById("btn-logout").addEventListener("click", () => {
  fetchEndpoint("/logout", "POST");
});

// Initial check
fetchEndpoint("/unprotected");
