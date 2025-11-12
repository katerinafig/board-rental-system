const API_BASE = 'http://localhost:8081/api/v1';
console.log(API_BASE)
let availableBoards = [];

// Navigation
function showSection(sectionName) {
    // Hide all sections
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });

    // Show selected section
    document.getElementById(sectionName).style.display = 'block';

    // Update navigation
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('isActive');
    });
    event.currentTarget.classList.add('isActive');

    // Load data for section
    if (sectionName === 'boards') loadBoards();
    if (sectionName === 'rent') loadAvailableBoardsForRent();
    if (sectionName === 'rentals') loadActiveRentals();

    // Scroll to content
    document.querySelector('.main-content').scrollIntoView({ behavior: 'smooth' });
}

// Load boards with filtering
async function loadBoards() {
    const boardType = document.getElementById('boardTypeFilter').value;
    let url = `${API_BASE}/boards/available`;

    if (boardType) {
        url = `${API_BASE}/boards/available/${boardType}`;
    }

    try {
        const response = await fetch(url);
        const boards = await response.json();
        displayBoards(boards);
    } catch (error) {
        console.error('Ошибка загрузки бордов:', error);
        showToast('Ошибка загрузки данных', 'error');
    }
}

// Display boards in grid
function displayBoards(boards) {
    const container = document.getElementById('boardsList');

    if (boards.length === 0) {
        container.innerHTML = `
            <div class="col-12">
                <div class="card text-center py-5">
                    <div class="card-body">
                        <i class="bi bi-inbox display-1 text-muted mb-3"></i>
                        <h5 class="card-title">Нет доступных бордов</h5>
                        <p class="text-muted">Попробуйте изменить фильтр или зайдите позже</p>
                    </div>
                </div>
            </div>
        `;
        return;
    }

    container.innerHTML = boards.map(board => `
        <div class="col-md-6 col-lg-4 col-xl-3">
            <div class="card board-card h-100">
                <div class="card-body d-flex flex-column">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <span class="badge board-type-${board.type} board-type-badge">
                            ${getBoardTypeIcon(board.type)} ${getBoardTypeName(board.type)}
                        </span>
                        <span class="badge ${board.isAvailable ? 'bg-success' : 'bg-danger'} status-badge">
                            ${board.isAvailable ? 'Доступен' : 'Занят'}
                        </span>
                    </div>

                    <h5 class="card-title">${board.name}</h5>

                    <div class="board-details flex-grow-1">
                        <div class="d-flex justify-content-between text-muted mb-2">
                            <span>Длина:</span>
                            <span>${board.length} см</span>
                        </div>
                        <div class="d-flex justify-content-between text-muted mb-2">
                            <span>Состояние:</span>
                            <span>${getConditionName(board.condition)}</span>
                        </div>
                    </div>

                    <div class="mt-auto">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Цена за час:</span>
                            <span class="board-price">${board.pricePerHour} ₽</span>
                        </div>

                        <div class="btn-group w-100">
                            ${board.isAvailable ? `
                                <button class="btn btn-primary btn-sm" onclick="quickRent(${board.id})">
                                    <i class="bi bi-cart-plus me-1"></i>Арендовать
                                </button>
                                <button class="btn btn-outline-primary btn-sm" onclick="showBoardDetails(${board.id})">
                                    <i class="bi bi-info-circle"></i>
                                </button>
                            ` : `
                                <button class="btn btn-secondary btn-sm w-100" disabled>
                                    <i class="bi bi-clock me-1"></i>Занят
                                </button>
                            `}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

// Quick rent function
function quickRent(boardId) {
    showSection('rent');

    // Ждем немного чтобы DOM обновился
    setTimeout(() => {
        const boardSelect = document.getElementById('boardSelect');
        boardSelect.value = boardId;
        updatePricePreview();
    }, 100);
}
// Load available boards for rent form
async function loadAvailableBoardsForRent() {
    try {
        const response = await fetch(`${API_BASE}/boards/available`);
        availableBoards = await response.json();
        const select = document.getElementById('boardSelect');

        select.innerHTML = '<option value="">-- Выберите борд --</option>' +
            availableBoards.filter(board => board.isAvailable).map(board => `
                <option value="${board.id}" data-price="${board.pricePerHour}">
                    ${board.name} (${getBoardTypeName(board.type)}) - ${board.pricePerHour} ₽/час
                </option>
            `).join('');

        updatePricePreview();
    } catch (error) {
        console.error('Ошибка загрузки бордов:', error);
        showToast('Ошибка загрузки данных', 'error');
    }
}

// Update price preview
function updatePricePreview() {
    const boardSelect = document.getElementById('boardSelect');
    const hoursInput = document.getElementById('rentalHours');
    const pricePreview = document.getElementById('pricePreview');
    const totalPreview = document.getElementById('totalPreview');

    const selectedBoard = availableBoards.find(board => board.id == boardSelect.value);

    if (selectedBoard) {
        const pricePerHour = selectedBoard.pricePerHour;
        const hours = parseInt(hoursInput.value);
        const total = pricePerHour * hours;

        pricePreview.textContent = `${pricePerHour} ₽`;
        totalPreview.textContent = `${total.toFixed(2)} ₽`;
    } else {
        pricePreview.textContent = '— ₽';
        totalPreview.textContent = '— ₽';
    }
}

// Create rental
document.getElementById('rentalForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const submitBtn = e.target.querySelector('button[type="submit"]');
    const originalText = submitBtn.innerHTML;

    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Обработка...';
    submitBtn.disabled = true;

    const formData = {
        boardId: parseInt(document.getElementById('boardSelect').value),
        customerName: document.getElementById('customerName').value,
        customerEmail: document.getElementById('customerEmail').value,
        rentalHours: parseInt(document.getElementById('rentalHours').value)
    };

    try {
        const response = await fetch(`${API_BASE}/rentals`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            showToast(`Аренда успешно создана!`, 'success');
            document.getElementById('rentalForm').reset();
            loadAvailableBoardsForRent();
            loadBoards();
        } else {
            showToast('Ошибка при создании аренды: ' + (result.message || 'Неизвестная ошибка'), 'error');
        }
    } catch (error) {
        showToast('Ошибка сети: ' + error.message, 'error');
    } finally {
        submitBtn.innerHTML = originalText;
        submitBtn.disabled = false;
    }
});

// Load active rentals
async function loadActiveRentals() {
    try {
        const response = await fetch(`${API_BASE}/rentals`);
        const rentals = await response.json();
        displayRentals(rentals);
    } catch (error) {
        console.error('Ошибка загрузки аренд:', error);
        showToast('Ошибка загрузки данных', 'error');
    }
}

// Display rentals
function displayRentals(rentals) {
    const container = document.getElementById('rentalsList');

    if (rentals.length === 0) {
        container.innerHTML = `
            <div class="col-12">
                <div class="card text-center py-5">
                    <div class="card-body">
                        <i class="bi bi-clock-history display-1 text-muted mb-3"></i>
                        <h5 class="card-title">Нет активных аренд</h5>
                        <p class="text-muted">У вас пока нет активных аренд</p>
                        <button class="btn btn-primary" onclick="showSection('boards')">
                            <i class="bi bi-search me-2"></i>Найти борд
                        </button>
                    </div>
                </div>
            </div>
        `;
        return;
    }

    container.innerHTML = rentals.map(rental => `
        <div class="col-12">
            <div class="card rental-item">
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-md-6">
                            <div class="d-flex align-items-center">
                                <div class="flex-shrink-0">
                                    <span class="badge board-type-${rental.board.type} board-type-badge me-3">
                                        ${getBoardTypeIcon(rental.board.type)}
                                    </span>
                                </div>
                                <div class="flex-grow-1">
                                    <h6 class="mb-1">${rental.board.name}</h6>
                                    <small class="text-muted">
                                        <i class="bi bi-person me-1"></i>${rental.customerName} •
                                        <i class="bi bi-envelope me-1"></i>${rental.customerEmail}
                                    </small>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="row text-center">
                                <div class="col-4">
                                    <small class="text-muted d-block">Часы</small>
                                    <strong>${rental.rentalHours}</strong>
                                </div>
                                <div class="col-4">
                                    <small class="text-muted d-block">Стоимость</small>
                                    <strong class="text-success">${rental.totalPrice} ₽</strong>
                                </div>
                                <div class="col-4">
                                    <small class="text-muted d-block">Дата</small>
                                    <strong>${new Date(rental.rentalDate).toLocaleDateString()}</strong>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2 text-end">
                            <button class="btn btn-outline-danger btn-sm" onclick="returnBoard(${rental.id})">
                                <i class="bi bi-arrow-return-left me-1"></i>Вернуть
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

// Return board
async function returnBoard(rentalId) {
    if (!confirm('Вы уверены, что хотите вернуть борд?')) return;

    try {
        const response = await fetch(`${API_BASE}/rentals/${rentalId}/return`, {
            method: 'POST'
        });

        if (response.ok) {
            showToast('Борд успешно возвращен!', 'success');
            loadActiveRentals();
            loadBoards();
        } else {
            showToast('Ошибка при возврате борда', 'error');
        }
    } catch (error) {
        showToast('Ошибка сети: ' + error.message, 'error');
    }
}

// Helper functions
function getBoardTypeName(type) {
    const names = {
        'SKATEBOARD': 'Скейтборд',
        'LONGBOARD': 'Лонгборд',
        'SURFBOARD': 'Серфборд',
        'SNOWBOARD': 'Сноуборд'
    };
    return names[type] || type;
}

function getBoardTypeIcon(type) {
    const icons = {
        'SKATEBOARD': '🎯',
        'LONGBOARD': '🛹',
        'SURFBOARD': '🏄',
        'SNOWBOARD': '🏂'
    };
    return icons[type] || '🎿';
}

function getConditionName(condition) {
    const names = {
        'NEW': 'Новый',
        'GOOD': 'Хорошее',
        'USED': 'Б/у'
    };
    return names[condition] || condition;
}

// Toast notifications
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `alert alert-${type === 'error' ? 'danger' : type} alert-dismissible fade show position-fixed`;
    toast.style.cssText = 'top: 100px; right: 20px; z-index: 9999; min-width: 300px;';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 5000);
}

// Show board details (placeholder)
function showBoardDetails(boardId) {
    showToast('Функция просмотра деталей в разработке', 'info');
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Update price preview when inputs change
    document.getElementById('boardSelect').addEventListener('change', updatePricePreview);
    document.getElementById('rentalHours').addEventListener('input', updatePricePreview);
    document.getElementById('rentalHoursRange').addEventListener('input', updatePricePreview);

    // Sync range and number inputs
    document.getElementById('rentalHours').addEventListener('input', function() {
        document.getElementById('rentalHoursRange').value = this.value;
    });

    // Load initial data
    loadBoards();
});