import * as actionTypes from '../actions/actionTypes';
import { updatedObject } from '../../shared/utility';
const initialState = {
    error: null,
    errorStatus: null,
    loading: false,
    id: null,
    firstName: null,
    lastName: null,
    email: null,
    active: false,
    imagePath: null,
    companyName: null,
    companyContact: null,
    gst: null,
    addresses: [],
    sellerAddress:[]
}
const customerStart = (state, action) => {
    return updatedObject(state, { error: null, loading: true })
}
const customerProfileSucc = (state, action) => {
    return updatedObject(state, {
        id: action.id,
        firstName: action.firstName,
        lastName: action.lastName,
        active: action.isActive,
        contact: action.contact,
        imagePath: action.imagePath,
        email: action.email,
        error: null,
        loading: false
    });
};
const customerFail = (state, action) => {
    return updatedObject(state, { error: action.error, errorStatus: action.errorStatus, loading: false })
}
const customerAddressesSucc = (state, action) => {
    return updatedObject(state, {
        addresses: action.addresses,
        error: null,
        loading: false
    });
};
const addAddressSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const deleteAddressSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const updatePasswordSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const updateAddressSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const updateCustomerProfileSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const sellerStart = (state, action) => {
    return updatedObject(state, { error: null, loading: true })
}
const sellerProfileSucc = (state, action) => {
    return updatedObject(state, {
        id: action.id,
        firstName: action.firstName,
        lastName: action.lastName,
        contact: action.contact,
        imagePath: action.imagePath,
        email: action.email,
        companyName: action.companyName,
        companyContact: action.contact,
        gst: action.gst,
        error: null,
        loading: false
    });
};
const sellerFail = (state, action) => {
    return updatedObject(state, { error: action.error, errorStatus: action.errorStatus, loading: false })
}
const sellerAddressSucc = (state, action) => {
    return updatedObject(state, {
        sellerAddress:action.sellerAddress,
        error: null,
        loading: false
    });
};
const updateSellerProfileSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const customerReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.CUSTOMER_START: return customerStart(state, action);
        case actionTypes.CUSTOMER_FAIL: return customerFail(state, action);
        case actionTypes.CUSTOMER_PROFILE_SUCCESS: return customerProfileSucc(state, action);
        case actionTypes.CUSTOMER_ADDRESSES_SUCCESS: return customerAddressesSucc(state, action);
        case actionTypes.ADD_ADDRESS_SUCCESS: return addAddressSucc(state, action);
        case actionTypes.DELETE_ADDRESS_SUCCESS: return deleteAddressSucc(state, action);
        case actionTypes.UPDATE_PASSWORD_SUCCESS: return updatePasswordSucc(state, action);
        case actionTypes.UPDATE_ADDRESS_SUCCESS: return updateAddressSucc(state, action);
        case actionTypes.UPDATE_CUSTOMER_PROFILE_SUCCESS: return updateCustomerProfileSucc(state, action);
        case actionTypes.SELLER_START: return sellerStart(state, action);
        case actionTypes.SELLER_FAIL: return sellerFail(state, action);
        case actionTypes.SELLER_PROFILE_SUCCESS: return sellerProfileSucc(state, action);
        case actionTypes.SELLER_ADDRESS_SUCCESS: return sellerAddressSucc(state, action);
        case actionTypes.UPDATE_SELLER_PROFILE_SUCCESS: return updateSellerProfileSucc(state, action);


        default: return state
    }

}

export default customerReducer;