import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from 'react-toastify';
export const adminStart = () => {
    return {
        type: actionTypes.ADMIN_START,
    };
}

export const adminFail = (error, errorStatus) => {
    return {
        type: actionTypes.ADMIN_FAIL,
        error: error,
        errorStatus: errorStatus
    };
}
export const adminListCustomerSuccess = (customers) => {
    return {
        type: actionTypes.ADMIN_LIST_CUSTOMER_SUCCESS,
        customers: customers
    };
}
export const adminListSellerSuccess = (sellers) => {
    return {
        type: actionTypes.ADMIN_LIST_SELLER_SUCCESS,
        sellers: sellers

    };
}

export const listAllCustomers = (token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/admin/getAllCustomer',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            dispatch(adminListCustomerSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const listAllSellers = (token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/admin/getAllSeller',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            dispatch(adminListSellerSuccess(response.data));
        }).catch(error => {
            alert(error.response.data)
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const activateDeactivateCustomerSuccess = (data) => {
    return {
        type: actionTypes.ACTIVATE_DEACTIVATE_CUSTOMER_SUCCESS
    };
}

export const activateDeactivateCustomer = (userId, choice, token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'PATCH',
            url: `http://localhost:8080/admin/activateDeactivateCustomer/${userId}?choice=${choice}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            if (response.data != null) {
                toast.success(response.data, {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(activateDeactivateCustomerSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}

export const adminListAllProductsSuccess = (data) => {
    return {
        type: actionTypes.ADMIN_VIEW_ALL_PRODUCTS,
        allProducts:data
    };
}

export const listAllProducts = (token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'GET',
            url: 'http://localhost:8080/admin/viewAllProducts',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            dispatch(adminListAllProductsSuccess(response.data));
        }).catch(error => {
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };
}


export const activateDeactivateProductSuccess = () => {
    return {
        type: actionTypes.ACTIVATE_DEACTIVATE_PRODUCT_SUCCESS
    };
}

export const activateDeactivateProduct = (productId, choice, token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'PUT',
            url: `http://localhost:8080/admin/activateDeactivateProduct/${productId}?choice=${choice}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            if (response.data != null) {
                toast.success(response.data, {
                    position: toast.POSITION.BOTTOM_LEFT,
                    autoClose: 5000
                })
            }
            dispatch(activateDeactivateCustomerSuccess(response.data));
        }).catch(error => {
            toast.error(error.response.data, {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}
export const adminViewProductDetailsSuccess = (data) => {
    return {
        type: actionTypes.ADMIN_VIEW_PRODUCT_DETAILS_SUCCESS,
        productDetails:data.productVariationDtoSet
    };
}

export const adminViewProductDetails = (productId,token) => {
    return dispatch => {
        dispatch(adminStart());
        axios({
            method: 'GET',
            url: `http://localhost:8080/admin/viewProduct/${productId}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then(response => {
            dispatch(adminViewProductDetailsSuccess(response.data));
        }).catch(error => {
            dispatch(adminFail(error.response.data.error, error.response.status))//It will give us the error generatd in the response data

        })
    };

}