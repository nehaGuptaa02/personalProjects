import * as actionTypes from '../actions/actionTypes';
import { updatedObject } from '../../shared/utility';
const initialState = {
    customers: [],
    sellers: [],
    error: null,
    errorStatus: null,
    loading: false,
    allProducts: [],
    productDetails:[]

}
const adminStart = (state, action) => {
    return updatedObject(state, { error: null, loading: true })
}
const adminFail = (state, action) => {
    return updatedObject(state, { error: action.error, errorStatus: action.errorStatus, loading: false })
}
const adminListCustomerSucc = (state, action) => {
    return updatedObject(state, {
        customers: action.customers,
        error: null,
        loading: false
    });
};
const adminListSellerSucc = (state, action) => {
    return updatedObject(state, {
        sellers: action.sellers,
        error: null,
        loading: false
    });
};
const activateDeactivateCustomerSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const adminViewAllProductsSucc = (state, action) => {
    return updatedObject(state, {
        allProducts: action.allProducts,
        error: null,
        loading: false
    });
};
const activateDeactivateProductSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const adminViewProductDetailsSucc = (state, action) => {
    return updatedObject(state, {
        productDetails:action.productDetails,
        error: null,
        loading: false
    });
};


const adminReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.ADMIN_START: return adminStart(state, action);
        case actionTypes.ADMIN_LIST_CUSTOMER_SUCCESS: return adminListCustomerSucc(state, action);
        case actionTypes.ADMIN_LIST_SELLER_SUCCESS: return adminListSellerSucc(state, action);
        case actionTypes.ADMIN_FAIL: return adminFail(state, action);
        case actionTypes.ACTIVATE_DEACTIVATE_CUSTOMER_SUCCESS: return activateDeactivateCustomerSucc(state, action);
        case actionTypes.ADMIN_VIEW_ALL_PRODUCTS: return adminViewAllProductsSucc(state, action);
        case actionTypes.ACTIVATE_DEACTIVATE_PRODUCT_SUCCESS: return activateDeactivateProductSucc(state, action);
        case actionTypes.ADMIN_VIEW_PRODUCT_DETAILS_SUCCESS: return adminViewProductDetailsSucc(state, action);
        default: return state
    }

}

export default adminReducer;