import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from 'react-toastify';
import { authOrders } from './auth';

export const productStart = () => {
    return {
        type: actionTypes.PRODUCT_START
    };
}

export const productFail = (error, errorStatus) => {
    return {
        type: actionTypes.PRODUCT_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const viewAllProductsSuccess = (data) => {
    return {
        type: actionTypes.VIEW_ALL_PRODUCTS_SUCCESS,
        products:data
    };
}
export const viewAllProducts = (subCategoryId, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/customer/viewAllProducts/${subCategoryId}`,
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            console.log('category products',response.data)
            // localStorage.setItem('childCategories', JSON.stringify(response.data));
            dispatch(viewAllProductsSuccess(response.data))
        }).catch(error => {
            alert(error.response.data)
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}
export const viewProductSuccess = (data) => {
    return {
        type: actionTypes.VIEW_PRODUCT_SUCCESS,
        productWithVariations:data
    };
}
export const viewProduct = (productId) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/unAuthViewProduct?id=${productId}`,
        }).then(response => {
            console.log('view product details',response.data)
            dispatch(viewProductSuccess(response.data))
        }).catch(error => {
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}

export const viewProductDetailsSuccess = (data) => {
    return {
        type: actionTypes.VIEW_PRODUCT_DETAILS_SUCCESS,
        productDetails: data
    };
}
export const viewProductDetails = (productId, variationId) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/viewProductDetail?productId=${productId}&variationId=${variationId}`,
        }).then(response => {
            console.log('productDetails',response.data);
            // localStorage.setItem('productDetails', JSON.stringify(response.data));
            dispatch(viewProductDetailsSuccess(response.data))
        }).catch(error => {
            alert(error.response.data)
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const addProductSuccess = (responseStatus) => {
    return {
        type: actionTypes.ADD_PRODUCT_SUCCESS,
        responseStatus: responseStatus
    };
}
export const addProduct = (name, brand, categoryId, description, isCancellable, isReturnable, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'POST',
            url: 'http://localhost:8080/seller/addProduct',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: {
                'name': name,
                'brand': brand,
                'categoryId': categoryId,
                'description': description,
                'isReturnable': isReturnable,
                'isCancellable': isCancellable
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your Product has been successfully added', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(addProductSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const viewAllProductsSellerSuccess = (data) => {
    return {
        type: actionTypes.VIEW_ALL_PRODUCTS_SELLER_SUCCESS,
        sellerProducts: data
    };
}
export const viewAllProductsSeller = (token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/seller/viewAllProducts',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(viewAllProductsSellerSuccess(response.data))
        }).catch(error => {
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}
export const searchProductSuccess = (data) => {
    console.log('data',data)
    return {
        type: actionTypes.SEARCH_PRODUCT_SUCCESS,
        searchResult:data
    };
}
export const searchProduct = (search) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/searchProducts?search=${search}`,
        }).then(response => {
            dispatch(searchProductSuccess(response.data))
        }).catch(error => {
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}
export const deleteProductSuccess = () => {
    return {
        type: actionTypes.DELETE_PRODUCT_SUCCESS
    };
}
export const deleteProduct = (id, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'DELETE',
            url: `http://localhost:8080/seller/deleteProduct/${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your Product has been successfully deleted', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(deleteProductSuccess())
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}
export const viewProductVariationsSuccess = (data) => {
    return {
        type: actionTypes.VIEW_PRODUCT_VARIATIONS_SUCCESS,
        sellerProductVariations: data
    };
}
export const viewProductVariations = (id, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/seller/viewAllProductVariations/${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(viewProductVariationsSuccess(response.data))
        }).catch(error => {
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const updateProductSuccess = () => {
    return {
        type: actionTypes.UPDATE_PRODUCT_SUCCESS
    };
}
export const updateProduct = (id, name, brand, description, isCancellable, isReturnable, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'PATCH',
            url: `http://localhost:8080/seller/updateProduct/${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: {
                'name': name,
                'brand': brand,
                'description': description,
                'isReturnable': isReturnable,
                'isCancellable': isCancellable
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Your Product has been updated successfully ', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(viewAllProductsSeller(token))
            dispatch(updateProductSuccess(response.status))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const addProductVariationSuccess = () => {
    return {
        type: actionTypes.ADD_PRODUCT_VARIATION_SUCCESS
    };
}
export const addProductVariation = (productId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token) => {
    var myObj =JSON.parse(JSON.stringify({ metadataKey : metadataValue}))
    // var myObj = JSON.parse({metadataKey: metadataValue});
    console.log('object',myObj)
    
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'POST',
            url: 'http://localhost:8080/seller/addProductVariation',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: {
                'productId': productId,
                'quantityAvailable': quantityAvailable,
                'price': price,
                // 'categoryId': categoryId,
                'primaryImageName': primaryImageName,
                 "metadata":myObj
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Product variation for the selected product has been successfully added ', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(addProductVariationSuccess())
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}


export const updateProductVariationSuccess = () => {
    return {
        type: actionTypes.UPDATE_PRODUCT_VARIATION_SUCCESS
    };
}
export const updateProductVariation = (productVariationId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'PATCH',
            url: `http://localhost:8080/seller/updateProductVariation/${productVariationId}`,
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: {
                'quantityAvailable': quantityAvailable,
                'price': price,
                'primaryImageName': primaryImageName,
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Product variation for the selected product has been successfully updated ', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(updateProductVariationSuccess())
            dispatch(viewProductVariations(productVariationId, token))
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const addToCartSuccess = () => {
    return {
        type: actionTypes.ADD_TO_CART_SUCCESS
    };
}
export const addToCart = (productId, variationId, name, price, image, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'POST',
            url: `http://localhost:8080/order/addOrderToCart?productId=${productId}&variationId=${variationId}&name=${name}&price=${price}&image=${image}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Product has been successfully added to the cart', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(addToCartSuccess())
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const deleteOrderSuccess = () => {
    return {
        type: actionTypes.DELETE_ORDER_SUCCESS
    };
}
export const ordersSuccess = (data) => {
    return {
        type: actionTypes.AUTH_FETCH_ORDERS_SUCCESS,
        authOrders:data
    };
}
export const orders = (token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/order/allOrdersByUser',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }).then(response => {
            console.log(response.data)
            // localStorage.setItem('orders', JSON.stringify(response.data));
            dispatch(ordersSuccess(response.data))
        }).catch(error => {
            console.log(error.response.data)
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}

export const deleteOrder = (id, token) => {
    return dispatch => {
        dispatch(productStart());
        axios({
            method: 'DELETE',
            url: `http://localhost:8080/order/deleteOrder?id=${id}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.data != null) {
                toast.success('Product has been successfully deleted from the cart', {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(deleteOrderSuccess())
            dispatch(authOrders(localStorage.getItem('token')));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
          
            dispatch(productFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data
        })
    };
}


