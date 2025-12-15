function openQuickView(productId, productName, productImage, productPrice) {
    const nameEl = document.getElementById('modalProductName');
    const imgEl = document.getElementById('modalProductImage');
    const priceEl = document.getElementById('modalProductPrice');
    const modalHost = document.getElementById('quickViewModal');

    if (!nameEl || !imgEl || !priceEl || !modalHost) {
        showNotification(`Xem nhanh: ${productName}`, 'info');
        return;
    }

    nameEl.textContent = productName;
    imgEl.src = productImage;
    priceEl.textContent = productPrice;

    const modal = new bootstrap.Modal(modalHost, {});
    modal.show();
}

function openQuickViewFromButton(button) {
    const productId = button.getAttribute('data-product-id');
    const productName = button.getAttribute('data-product-name');
    const productImage = button.getAttribute('data-product-image');
    const productPrice = button.getAttribute('data-product-price');
    
    openQuickView(productId, productName, productImage, productPrice);
}

function increaseQuantity() {
    const quantityInput = document.getElementById('quantityInput');
    let currentValue = parseInt(quantityInput.value);
    const maxValue = parseInt(quantityInput.getAttribute('max'));

    if (currentValue < maxValue) {
        quantityInput.value = currentValue + 1;
    }
}

function decreaseQuantity() {
    const quantityInput = document.getElementById('quantityInput');
    let currentValue = parseInt(quantityInput.value);
    const minValue = parseInt(quantityInput.getAttribute('min'));

    if (currentValue > minValue) {
        quantityInput.value = currentValue - 1;
    }
}

function addToCart() {
    const quantity = document.getElementById('quantityInput') ? document.getElementById('quantityInput').value : 1;
    const productNameEl = document.querySelector('.detail-title');
    const productName = productNameEl ? productNameEl.textContent : 'Product';

    showNotification(`Đã thêm ${quantity} sản phẩm "${productName}" vào giỏ hàng`, 'success');
    updateCartBadge(parseInt(quantity));
}

function buyNow() {
    const quantity = document.getElementById('quantityInput') ? document.getElementById('quantityInput').value : 1;
    showNotification('Chuyển tới trang thanh toán...', 'info');
}

function updateCartBadge(count) {
    const cartBadge = document.querySelector('.icon-badge');
    if (cartBadge) {
        let currentCount = parseInt(cartBadge.textContent) || 0;
        cartBadge.textContent = currentCount + count;
    }
}

function changeMainImage(thumbnail) {
    const allThumbnails = document.querySelectorAll('.thumbnail-item');
    allThumbnails.forEach(item => item.classList.remove('active'));

    thumbnail.closest('.thumbnail-item').classList.add('active');

    const mainImage = document.getElementById('mainProductImage');
    if (mainImage) {
        mainImage.src = thumbnail.src;
    }
}

function showNotification(message, type = 'info') {
    const toast = document.createElement('div');
    const typeMap = {
        'success': 'success',
        'error': 'danger',
        'warning': 'warning',
        'info': 'info'
    };

    toast.className = `alert alert-${typeMap[type]} alert-dismissible fade show`;
    toast.setAttribute('role', 'alert');
    toast.setAttribute('style', `
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 9999;
        min-width: 300px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        animation: slideIn 0.3s ease;
    `);

    let icon = 'fa-info-circle';
    if (type === 'success') icon = 'fa-check-circle';
    else if (type === 'error') icon = 'fa-exclamation-circle';
    else if (type === 'warning') icon = 'fa-exclamation-triangle';

    toast.innerHTML = `
        <i class="fas ${icon}"></i> ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 4000);
}

const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
`;
document.head.appendChild(style);

document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.querySelector('.search-bar');
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const searchInput = this.querySelector('input');
            if (searchInput && searchInput.value.trim()) {
                return true;
            }
        });
    }

    const newsletterForm = document.querySelector('.newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const email = this.querySelector('input[type="email"]').value;
            if (email) {
                showNotification(`Cảm ơn! Chúng tôi đã gửi xác nhận tới ${email}`, 'success');
                this.reset();
            }
        });
    }

    const addToCartButtons = document.querySelectorAll('.btn-add-cart');
    addToCartButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const isLink = this.tagName.toLowerCase() === 'a';
            if (!isLink) {
                e.preventDefault();

                const productCard = this.closest('.product-card');
                if (productCard) {
                    const productName = productCard.querySelector('.product-name').textContent;
                    const productPrice = productCard.querySelector('.product-price-new').textContent;

                    showNotification(`Đã thêm "${productName}" (${productPrice}) vào giỏ hàng`, 'success');
                    updateCartBadge(1);

                    this.innerHTML = '<i class="fas fa-check"></i> Đã thêm';
                    this.style.backgroundColor = '#4caf50';
                    this.style.borderColor = '#4caf50';
                    this.style.color = 'white';

                    setTimeout(() => {
                        this.innerHTML = '<i class="fas fa-shopping-cart"></i> Thêm giỏ';
                        this.style.backgroundColor = '';
                        this.style.borderColor = '';
                        this.style.color = '';
                    }, 1500);
                }
            }
        });
    });

    const modalAddToCartBtn = document.querySelector('.btn-modal-cart');
    if (modalAddToCartBtn) {
        modalAddToCartBtn.addEventListener('click', function() {
            const productName = document.getElementById('modalProductName').textContent;
            showNotification(`Đã thêm "${productName}" vào giỏ hàng`, 'success');
            updateCartBadge(1);

            this.innerHTML = '<i class="fas fa-check"></i> Đã thêm vào giỏ';
            setTimeout(() => {
                this.innerHTML = '<i class="fas fa-shopping-cart"></i> Thêm vào giỏ';
            }, 1500);
        });
    }
});

window.openQuickView = openQuickView;
window.increaseQuantity = increaseQuantity;
window.decreaseQuantity = decreaseQuantity;
window.addToCart = addToCart;
window.buyNow = buyNow;
window.changeMainImage = changeMainImage;
window.showNotification = showNotification;
