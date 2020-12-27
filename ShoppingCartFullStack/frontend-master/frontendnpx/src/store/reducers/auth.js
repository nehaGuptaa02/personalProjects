import * as actionTypes from '../actions/actionTypes';
import { updatedObject } from '../../shared/utility';

const initialState = {
    token: null,
    error: null,
    errorStatus: null,
    loading: false,
    authRedirectPath: '/',
    id: null,
    firstName: null,
    lastName: null,
    email: null,
    active: false,
    companyName: null,
    gst: null,
    contact: null,
    signUpType: null,
    // parentCategories:[],
    parentCategories:[],
    responseStatus:null,
    authProducts:[],
    authOrders:[]
}
const authStart = (state, action) => {
    return updatedObject(state, { error: null, loading: true,responseStatus:null })
}
const authSuccess = (state, action) => {
    return updatedObject(state, {
        token: action.idToken,
        email:action.email,
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
};
const authFail = (state, action) => {
    return updatedObject(state, { error: action.error, 
        errorStatus: action.errorStatus, 
        loading: false,
    responseStatus:null })
}

const authLogout = (state, action) => {
    return updatedObject(state, { token: null, userId: null })
}
const setAuthRedirectPath = (state, action) => {
    return updatedObject(state, { authRedirectPath: action.path })
}
const signUpCustomerSucc = (state, action) => {
    return updatedObject(state, {
        id: action.id,
        firstName: action.firstName,
        lastName: action.lastName,
        email: action.email,
        active: action.active,
        contact: action.contact,
        signUpType: action.signUpType,
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
}
const signUpSellerSucc = (state, action) => {
    return updatedObject(state, {
        id: action.id,
        firstName: action.firstName,
        lastName: action.lastName,
        email: action.email,
        active: action.active,
        contact: action.companyContact,
        companyName:action.companyContact,
        signUpType: action.signUpType,
        error: null,
        loading: false,
        responseStatus:action.responseStatus

    });
}
const userAccountActivationSucc = (state, action) => {
    return updatedObject(state, {
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
};
const resendActivationLinkSucc = (state, action) => {
    return updatedObject(state, {
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
};
const forgotPasswordSucc = (state, action) => {
    return updatedObject(state, {
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
};
const forgotPasswordResetSucc = (state, action) => {
    return updatedObject(state, {
        responseStatus:action.responseStatus,
        error: null,
        loading: false
    });
};
const getParentCategoriesSucc = (state, action) => {
    console.log('reducer',action.parentCategories);
    return updatedObject(state, {
        parentCategories: action.parentCategories,
        error: null,
        loading: false
    });
};
const authProductsSucc = (state, action) => {
    return updatedObject(state, {
        authProducts:action.authProducts,
        error: null,
        loading: false
    });
};
const authFetchOrdersSucc = (state, action) => {
    return updatedObject(state, {
        authOrders:action.authOrders,
        error: null,
        loading: false
    });
};
const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.AUTH_START: return authStart(state, action);
        case actionTypes.AUTH_SUCCESS: return authSuccess(state, action);
        case actionTypes.AUTH_FAIL: return authFail(state, action);
        case actionTypes.AUTH_LOGOUT: return authLogout(state, action);
        case actionTypes.SET_AUTH_REDIRECT_PATH: return setAuthRedirectPath(state, action);
        case actionTypes.SIGN_UP_CUSTOMER_SUCCESS: return signUpCustomerSucc(state, action);
        case actionTypes.SIGN_UP_SELLER_SUCCESS: return signUpSellerSucc(state, action);
        case actionTypes.USER_ACTIVATE_SUCCESS:return userAccountActivationSucc(state,action);
        case actionTypes.RESEND_ACTIVATION_LINK_SUCCESS:return resendActivationLinkSucc(state,action);
        case actionTypes.FORGOT_PASSWORD_LINK_SUCCESS:return forgotPasswordSucc(state,action);
        case actionTypes.FORGOT_PASSWORD_RESET_SUCCESS:return forgotPasswordResetSucc(state,action);
        case actionTypes.GET_PARENT_CATEGORIES:return getParentCategoriesSucc(state,action);
        case actionTypes.AUTH_PRODUCTS_SUCCESS:return authProductsSucc(state,action);
        case actionTypes.AUTH_FETCH_ORDERS_SUCCESS:return authFetchOrdersSucc(state,action);



        default: return state
    }

}

export default authReducer;