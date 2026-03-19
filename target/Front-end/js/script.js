
"use strict"  
const form = document.querySelector('form');
const msg = document.getElementById('msg-feedback');

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const confirmEmail = document.getElementById('confirm-email').value
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    console.log("Valor da Senha 1:", password);
    console.log("Valor da Senha 2:", confirmPassword);

    if (email !== confirmEmail){
        msg.style.display = 'block';
        msg.style.backgroundColor = "#ff4d4d";
        msg.innerHTML = "<strong>Error:</strong> The emails don't match! Please  try again.";
        return
    }
    if (password !== confirmPassword){
        msg.style.display = 'block';
        msg.style.backgroundColor = "#ff4d4d";
        msg.innerHTML = "<strong>Error:</strong> The passwords don't match! Please try again.";
        return
    }

    msg.style.display = 'block';
    msg.style.backgroundColor = "#2ecc71"
    msg.style.color = "#ffffff";
    msg.innerHTML = "Registration successful! Redirecting...";

    setTimeout(function() {
        window.location.href = "index.html";
    }, 3000);

});