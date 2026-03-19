
"use strict"  
const form = document.querySelector('form');
const msg = document.getElementById('msg-feedback');

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const confirmEmail = document.getElementById('confirm-email').value
    const password = document.getElementById('password').Value;
    const confirmPassword = document.getElementById('confirm-password').value;

    if (email != confirmEmail){
        msg.style.display = 'block';
        msg.style.backgroundColor = "#ff4d4d";
        msg.innerHTML = "<strong>Error:</strong> The emails don't match! Please  try again.";
        return
    }
    if (password != confirmPassword){
        msg.style.display = 'block';
        msg.style.backgroundColor = "#ff4d4d";
        msg.innerHTML = "<strong>Error:</strong> The passwords don't match! Please try again.";
        return
    }

    msgFeedback.style.display = 'block';
    msg.style.backgroundColor = "#000000"
    msg.style.color = "#000";
    msgFeedback.innerHTML = "Registration successful! Redirecting...";

    setTimeout(function() {
        window.location.href = "index.html";
    }, 3000);

});