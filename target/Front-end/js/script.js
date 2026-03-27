
"use strict"

const form = document.querySelector('form');
const msg = document.getElementById('msg-feedback');

// --- LOGIN ---
const formLogin = document.getElementById('login-form');
if (formLogin) {
    formLogin.addEventListener('submit', function(event) {
        const emailLogin = document.getElementById('email').value;
        const passwordLogin = document.getElementById('password').value;

        if (emailLogin === '' || passwordLogin === '') {
            event.preventDefault();
            alert('Please fill in your email and password to enter.');
            return;
        } else {
            event.preventDefault(); // Apenas para teste local
            alert('Login successfully performed! Welcome');
            window.location.href = "home.html";
        }
    });
}

// --- SHOW/HIDE PASSWORD ---
const eyeButton = document.getElementById('btn-eye');
const fieldPassword = document.getElementById('password');
if (eyeButton && fieldPassword) {
    eyeButton.addEventListener('click', function() {
        if (fieldPassword.type === 'password') {
            fieldPassword.type = 'text';
            eyeButton.textContent = '🔒';
        } else {
            fieldPassword.type = 'password';
            eyeButton.textContent = '👁️';
        }
    });
}

// --- REGISTER ---
// Só roda se NÃO for o formulário de login e se os campos existirem
if (form && !formLogin) {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const confirmEmail = document.getElementById('confirm-email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        
        // Novos campos
        const cpf = document.getElementById('cpf').value;
        const phone = document.getElementById('phone').value;
        const fullName = document.getElementById('full-name').value;

        // Validation: Full Name
        const nameParts = fullName.trim().split(' ');
        if (nameParts.length < 2) {
            msg.style.display = 'block';
            msg.style.backgroundColor = "#ff4d4d";
            msg.innerHTML = "<strong>Error:</strong> Please enter your full name.";
            return;
        }

        // Validation: CPF/Phone length
        if (cpf.length < 14 || phone.length < 14) {
            msg.style.display = 'block';
            msg.style.backgroundColor = "#ff4d4d";
            msg.innerHTML = "<strong>Error:</strong> CPF or Phone is incomplete!";
            return;
        }

        if (email !== confirmEmail) {
            msg.style.display = 'block';
            msg.style.backgroundColor = "#ff4d4d";
            msg.innerHTML = "<strong>Error:</strong> The emails don't match!";
            return;
        }

        if (password !== confirmPassword) {
            msg.style.display = 'block';
            msg.style.backgroundColor = "#ff4d4d";
            msg.innerHTML = "<strong>Error:</strong> The passwords don't match!";
            return;
        }

        msg.style.display = 'block';
        msg.style.backgroundColor = "#2ecc71";
        msg.style.color = "#ffffff";
        msg.innerHTML = "Registration successful! Redirecting...";

        setTimeout(function() {
            window.location.href = "index.html";
        }, 3000);
    });
}

// --- MASKS (Só rodam se os campos existirem) ---
const fieldCPF = document.getElementById('cpf');
const fieldPhone = document.getElementById('phone');

if (fieldCPF) {
    fieldCPF.addEventListener('input', function(e) {
        // 1. Pega apenas os números
        let v = e.target.value.replace(/\D/g, "");

        // 2. Limita a 11 dígitos
        if (v.length > 11) v = v.slice(0, 11);

        // 3. Aplica a máscara de uma vez só baseada na quantidade de números
        if (v.length <= 3) {
            e.target.value = v;
        } else if (v.length <= 6) {
            e.target.value = v.replace(/(\d{3})(\d+)/, "$1.$2");
        } else if (v.length <= 9) {
            e.target.value = v.replace(/(\d{3})(\d{3})(\d+)/, "$1.$2.$3");
        } else {
            e.target.value = v.replace(/(\d{3})(\d{3})(\d{3})(\d+)/, "$1.$2.$3-$4");
        }
    });
}

if (fieldPhone) {
    fieldPhone.addEventListener('input', function(e) {
        let v = e.target.value.replace(/\D/g, "");
        if (v.length > 11) v = v.slice(0, 11);
        v = v.replace(/^(\d{2})(\d)/g, "($1) $2");
        v = v.replace(/(\d{5})(\d)/, "$1-$2");
        e.target.value = v;
    });
}