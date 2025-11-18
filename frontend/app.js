const API_URL = 'https://bug-free-fiesta-69gwgg75gpr4hx4rj-8080.app.github.dev';

document.addEventListener('DOMContentLoaded', () => {
    setupNavigation();
    setupFormListeners();
    setupActionListeners(); 
    setupModalClose(); 
    loadEmpresas(); 
});

// --- NAVEGAÇÃO ---
function setupNavigation() {
    const navButtons = document.querySelectorAll('.nav-button');
    const sections = document.querySelectorAll('.content-section');

    navButtons.forEach(button => {
        button.addEventListener('click', () => {
            const targetId = button.dataset.target;
            
            navButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            
            sections.forEach(section => {
                if (section.id === targetId) {
                    section.classList.add('active');
                    loadDataForSection(targetId);
                } else {
                    section.classList.remove('active');
                }
            });
            resetAllForms();
        });
    });
}

// --- CARREGAMENTO DE DADOS (GET) ---

function loadDataForSection(sectionId) {
    if (sectionId === 'empresas') {
        loadEmpresas();
    } else if (sectionId === 'estudantes') {
        loadEstudantes();
    } else if (sectionId === 'vagas') {
        loadVagas();
        populateEmpresasDropdown(); 
    } else if (sectionId === 'inscricoes') {
        loadInscricoes();
        populateEstudantesDropdown(); 
        populateVagasDropdown();      
    }
}

// GET: Empresas
async function loadEmpresas() {
    try {
        const response = await fetch(`${API_URL}/empresas`);
        if (!response.ok) throw new Error('Erro ao carregar empresas');
        const empresas = await response.json();
        
        const tbody = document.querySelector('#tabela-empresas tbody');
        tbody.innerHTML = '';
        
        empresas.forEach(emp => {
            tbody.innerHTML += `
                <tr data-id="${emp.id}" 
                    data-nome="${emp.nomeFantasia}" 
                    data-cnpj="${emp.cnpj}" 
                    data-email="${emp.emailContato}">
                    <td>${emp.id}</td>
                    <td>${emp.nomeFantasia}</td>
                    <td>${emp.cnpj}</td>
                    <td>
                        <button class="action-btn btn-edit" data-type="empresas">Editar</button>
                        <button class="action-btn btn-delete" data-type="empresas">Deletar</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

// GET: Estudantes
async function loadEstudantes() {
    try {
        const response = await fetch(`${API_URL}/estudantes`);
        if (!response.ok) throw new Error('Erro ao carregar estudantes');
        const estudantes = await response.json();
        
        const tbody = document.querySelector('#tabela-estudantes tbody');
        tbody.innerHTML = '';
        
        estudantes.forEach(est => {
            tbody.innerHTML += `
                <tr data-id="${est.id}"
                    data-nome="${est.nome}"
                    data-email="${est.email}"
                    data-anoingresso="${est.anoIngresso}">
                    <td>${est.id}</td>
                    <td>${est.nome}</td>
                    <td>${est.email}</td>
                    <td>${est.anoIngresso}</td>
                    <td>
                        <button class="action-btn btn-edit" data-type="estudantes">Editar</button>
                        <button class="action-btn btn-delete" data-type="estudantes">Deletar</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

// GET: Vagas (COM MUDANÇAS)
async function loadVagas() {
    try {
        const response = await fetch(`${API_URL}/vagas`);
        if (!response.ok) throw new Error('Erro ao carregar vagas');
        const vagas = await response.json();
        
        const tbody = document.querySelector('#tabela-vagas tbody');
        tbody.innerHTML = '';
        
        vagas.forEach(vaga => {
            tbody.innerHTML += `
                <tr data-id="${vaga.id}"
                    data-titulo="${vaga.titulo}"
                    data-descricao="${vaga.descricao || ''}"
                    data-datapublicacao="${vaga.dataPublicacao}"
                    data-ativo="${vaga.ativo}"
                    data-empresa_id="${vaga.empresa ? vaga.empresa.id : ''}">
                    <td>${vaga.id}</td>
                    <td>${vaga.titulo}</td>
                    <td>${vaga.empresa ? vaga.empresa.nomeFantasia : 'N/A'}</td>
                    <td>${vaga.ativo ? 'Sim' : 'Não'}</td>
                    <td>
                        <button class="action-btn btn-view" data-type="vagas">Ver Mais</button>
                        <button class="action-btn btn-edit" data-type="vagas">Editar</button>
                        <button class="action-btn btn-delete" data-type="vagas">Deletar</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

// GET: Inscrições
async function loadInscricoes() {
    try {
        const response = await fetch(`${API_URL}/inscricoes`);
        if (!response.ok) throw new Error('Erro ao carregar inscrições');
        const inscricoes = await response.json();
        
        const tbody = document.querySelector('#tabela-inscricoes tbody');
        tbody.innerHTML = '';
        
        inscricoes.forEach(insc => {
            tbody.innerHTML += `
                <tr data-id="${insc.id}"
                    data-datainscricao="${insc.dataInscricao}"
                    data-status="${insc.status}"
                    data-mensagemapresentacao="${insc.mensagemApresentacao || ''}"
                    data-estudante_id="${insc.estudante ? insc.estudante.id : ''}"
                    data-vaga_id="${insc.vaga ? insc.vaga.id : ''}">
                    <td>${insc.id}</td>
                    <td>${insc.estudante ? insc.estudante.nome : 'N/A'}</td>
                    <td>${insc.vaga ? insc.vaga.titulo : 'N/A'}</td>
                    <td>${insc.status}</td>
                    <td>
                        <button class="action-btn btn-view" data-type="inscricoes">Ver Mais</button>
                        <button class="action-btn btn-edit" data-type="inscricoes">Editar</button>
                        <button class="action-btn btn-delete" data-type="inscricoes">Deletar</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}


// --- POPULAR DROPDOWNS ---
async function populateEmpresasDropdown() {
    try {
        const response = await fetch(`${API_URL}/empresas`);
        const empresas = await response.json();
        const select = document.getElementById('vaga-empresa');
        select.innerHTML = '<option value="">Selecione a empresa...</option>';
        empresas.forEach(emp => {
            select.innerHTML += `<option value="${emp.id}">${emp.nomeFantasia}</option>`;
        });
    } catch (error) {
        console.error('Erro ao popular empresas', error);
    }
}

async function populateEstudantesDropdown() {
    try {
        const response = await fetch(`${API_URL}/estudantes`);
        const estudantes = await response.json();
        const select = document.getElementById('insc-estudante');
        select.innerHTML = '<option value="">Selecione o estudante...</option>';
        estudantes.forEach(est => {
            select.innerHTML += `<option value="${est.id}">${est.nome}</option>`;
        });
    } catch (error) {
        console.error('Erro ao popular estudantes', error);
    }
}

async function populateVagasDropdown() {
    try {
        const response = await fetch(`${API_URL}/vagas`);
        const vagas = await response.json();
        const select = document.getElementById('insc-vaga');
        select.innerHTML = '<option value="">Selecione a vaga...</option>';
        vagas.filter(v => v.ativo).forEach(vaga => { // Mostra apenas vagas ativas
            select.innerHTML += `<option value="${vaga.id}">${vaga.titulo}</option>`;
        });
    } catch (error) {
        console.error('Erro ao popular vagas', error);
    }
}

// --- CRIAÇÃO E ATUALIZAÇÃO (POST / PUT) ---
function setupFormListeners() {
    // Form: Empresa
    document.getElementById('form-criar-empresa').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            nomeFantasia: document.getElementById('emp-nome').value,
            cnpj: document.getElementById('emp-cnpj').value,
            emailContato: document.getElementById('emp-email').value
        };
        
        if (form.dataset.editingId) { // Se tem ID, é PUT (Editar)
            await putData('empresas', form.dataset.editingId, data);
        } else { // Se não, é POST (Criar)
            await postData('empresas', data);
        }
        
        loadEmpresas();
        resetForm(form, 'Criar Nova Empresa', 'Salvar Empresa');
    });

    // Form: Estudante
    document.getElementById('form-criar-estudante').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            nome: document.getElementById('est-nome').value,
            email: document.getElementById('est-email').value,
            anoIngresso: document.getElementById('est-ano').value
        };
        
        if (form.dataset.editingId) {
            await putData('estudantes', form.dataset.editingId, data);
        } else {
            await postData('estudantes', data);
        }
        
        loadEstudantes();
        resetForm(form, 'Criar Novo Estudante', 'Salvar Estudante');
    });

    // Form: Vaga
    document.getElementById('form-criar-vaga').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            titulo: document.getElementById('vaga-titulo').value,
            descricao: document.getElementById('vaga-descricao').value,
            dataPublicacao: document.getElementById('vaga-data').value,
            ativo: document.getElementById('vaga-ativo').checked,
            empresa: {
                id: document.getElementById('vaga-empresa').value // Envia o objeto aninhado
            }
        };

        if (form.dataset.editingId) {
            await putData('vagas', form.dataset.editingId, data);
        } else {
            await postData('vagas', data);
        }

        loadVagas();
        resetForm(form, 'Criar Nova Vaga', 'Salvar Vaga');
    });

    // Form: Inscrição
    document.getElementById('form-criar-inscricao').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            dataInscricao: document.getElementById('insc-data').value,
            status: document.getElementById('insc-status').value,
            mensagemApresentacao: document.getElementById('insc-msg').value,
            estudante: { id: document.getElementById('insc-estudante').value },
            vaga: { id: document.getElementById('insc-vaga').value }
        };

        if (form.dataset.editingId) {
            await putData('inscricoes', form.dataset.editingId, data);
        } else {
            await postData('inscricoes', data);
        }
        
        loadInscricoes();
        resetForm(form, 'Realizar Nova Inscrição', 'Salvar Inscrição');
    });
}

// Reseta o formulário, o título e o botão
function resetForm(formElement, formTitle, submitButtonText) {
    formElement.reset(); // Limpa os campos
    delete formElement.dataset.editingId; // Remove o ID de edição
    formElement.querySelector('button[type="submit"]').textContent = submitButtonText; // Restaura o texto do botão
    formElement.previousElementSibling.textContent = formTitle; // Restaura o título do formulário
}

// Limpa TODOS os formulários (usado ao trocar de aba)
function resetAllForms() {
    resetForm(document.getElementById('form-criar-empresa'), 'Criar Nova Empresa', 'Salvar Empresa');
    resetForm(document.getElementById('form-criar-estudante'), 'Criar Novo Estudante', 'Salvar Estudante');
    resetForm(document.getElementById('form-criar-vaga'), 'Criar Nova Vaga', 'Salvar Vaga');
    resetForm(document.getElementById('form-criar-inscricao'), 'Realizar Nova Inscrição', 'Salvar Inscrição');
}

// Função Genérica de POST
async function postData(endpoint, data) {
    try {
        const response = await fetch(`${API_URL}/${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
             const errorData = await response.json();
             throw new Error(errorData.message || `Erro ao salvar em ${endpoint}`);
        }
        alert('Item salvo com sucesso!');
    } catch (error) {
        console.error(error);
        alert(`Erro ao salvar: ${error.message}`);
    }
}

// Função Genérica de PUT (Editar)
async function putData(endpoint, id, data) {
    try {
        data.id = id; 
        
        const response = await fetch(`${API_URL}/${endpoint}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Erro ao atualizar em ${endpoint}`);
        }
        alert('Item atualizado com sucesso!');
    } catch (error) {
        console.error(error);
        alert(`Erro ao atualizar: ${error.message}`);
    }
}


// --- AÇÕES (Ver Mais, Editar, Deletar) ---
function setupActionListeners() {
    const content = document.querySelector('.content');
    
    content.addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-delete')) {
            handleDelete(e.target);
        }
        if (e.target.classList.contains('btn-view')) {
            handleView(e.target);
        }
        if (e.target.classList.contains('btn-edit')) {
            handleEdit(e.target);
        }
    });
}

// Ação: Deletar (DELETE)
async function handleDelete(button) {
    const tr = button.closest('tr');
    const id = tr.dataset.id;
    const type = tr.closest('table').id.split('-')[1]; // 'empresas', 'estudantes', etc.

    if (confirm(`Tem certeza que deseja deletar o item ID ${id}?`)) {
        try {
            const response = await fetch(`${API_URL}/${type}/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                let errorMsg = `Erro ao deletar`;
                try {
                    const errorText = await response.text();
                    errorMsg = errorText || errorMsg;
                } catch (e) {}
                throw new Error(errorMsg);
            }
            alert('Item deletado com sucesso!');
            loadDataForSection(type);
        } catch (error) {
            console.error(error);
            alert(`Erro ao deletar: ${error.message}`);
        }
    }
}

// Ação: Ver Mais (Abre o Modal)
function handleView(button) {
    const data = button.closest('tr').dataset;
    const type = button.closest('table').id.split('-')[1];
    
    const modal = document.getElementById('view-modal');
    const title = document.getElementById('modal-title');
    const body = document.getElementById('modal-body');
    
    body.innerHTML = '';
    
    if (type === 'vagas') {
        title.textContent = 'Detalhes da Vaga';
        body.innerHTML = `
            <p><strong>Título:</strong> ${data.titulo}</p>
            <p><strong>Descrição:</strong> ${data.descricao || 'N/A'}</p>
            <p><strong>Publicação:</strong> ${new Date(data.datapublicacao).toLocaleDateString()}</p>
            <p><strong>Ativo:</strong> ${data.ativo === 'true' ? 'Sim' : 'Não'}</p>
        `;
    } else if (type === 'inscricoes') {
        title.textContent = 'Detalhes da Inscrição';
        body.innerHTML = `
            <p><strong>Data:</strong> ${new Date(data.datainscricao).toLocaleDateString()}</p>
            <p><strong>Status:</strong> ${data.status}</p>
            <p><strong>Mensagem:</strong> ${data.mensagemapresentacao || 'N/A'}</p>
        `;
    }
    
    modal.style.display = 'flex';
}

// Ação: Editar (Preenche o formulário)
function handleEdit(button) {
    const data = button.closest('tr').dataset;
    const type = button.closest('table').id.split('-')[1];

    if (type === 'vagas') {
        const form = document.getElementById('form-criar-vaga');
        document.getElementById('vaga-titulo').value = data.titulo;
        document.getElementById('vaga-descricao').value = data.descricao;
        document.getElementById('vaga-data').value = data.datapublicacao;
        document.getElementById('vaga-ativo').checked = (data.ativo === 'true');
        document.getElementById('vaga-empresa').value = data.empresa_id;
        
        form.dataset.editingId = data.id; // <-- Guarda o ID para o submit!
        form.querySelector('h2').textContent = `Editando Vaga ID: ${data.id}`;
        form.querySelector('button[type="submit"]').textContent = 'Atualizar Vaga';
        
    } else if (type === 'empresas') {
        const form = document.getElementById('form-criar-empresa');
        document.getElementById('emp-nome').value = data.nome;
        document.getElementById('emp-cnpj').value = data.cnpj;
        document.getElementById('emp-email').value = data.email;

        form.dataset.editingId = data.id;
        form.querySelector('h2').textContent = `Editando Empresa ID: ${data.id}`;
        form.querySelector('button[type="submit"]').textContent = 'Atualizar Empresa';

    } else if (type === 'estudantes') {
        const form = document.getElementById('form-criar-estudante');
        document.getElementById('est-nome').value = data.nome;
        document.getElementById('est-email').value = data.email;
        document.getElementById('est-ano').value = data.anoingresso;

        form.dataset.editingId = data.id;
        form.querySelector('h2').textContent = `Editando Estudante ID: ${data.id}`;
        form.querySelector('button[type="submit"]').textContent = 'Atualizar Estudante';

    } else if (type === 'inscricoes') {
        const form = document.getElementById('form-criar-inscricao');
        document.getElementById('insc-data').value = data.datainscricao;
        document.getElementById('insc-status').value = data.status;
        document.getElementById('insc-msg').value = data.mensagemapresentacao;
        document.getElementById('insc-estudante').value = data.estudante_id;
        document.getElementById('insc-vaga').value = data.vaga_id;
        
        form.dataset.editingId = data.id;
        form.querySelector('h2').textContent = `Editando Inscrição ID: ${data.id}`;
        form.querySelector('button[type="submit"]').textContent = 'Atualizar Inscrição';
    }
    
    document.querySelector('.content').scrollTo(0, 0);
}

// Lógica para Fechar o Modal
function setupModalClose() {
    const modal = document.getElementById('view-modal');
    const closeBtn = document.getElementById('modal-close-btn');
    
    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });
    
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
}