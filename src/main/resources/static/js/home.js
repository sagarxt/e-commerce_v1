document.addEventListener("DOMContentLoaded", function () {
    const loginModalOpenElement = document.getElementById("loginModalOpen");
    const loginModalOpen = loginModalOpenElement ? loginModalOpenElement.value === "true" : false;

    if (loginModalOpen) {
        new bootstrap.Modal(document.getElementById("loginModal")).show();
    }

    const signupModalOpenElement = document.getElementById("signupModalOpen");
    const signupModalOpen = signupModalOpenElement ? signupModalOpenElement.value === "true" : false;

    const otpSentElement = document.getElementById("otpSent");
    const otpSent = otpSentElement ? otpSentElement.value === "true" : false;

    if (signupModalOpen || otpSent) {
        new bootstrap.Modal(document.getElementById("signupModal")).show();
    }
});

// Handle login form submission
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    const formData = new FormData(this);

    fetch('/perform_login', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/redirect'; // Redirect based on roles
        } else {
            document.getElementById('loginError').classList.remove('d-none');
            document.getElementById('loginError').textContent = 'Invalid email or password';
        }
    })
    .catch(error => {
        console.error('Error during login:', error);
    });
});

console.log("Home.js loaded");