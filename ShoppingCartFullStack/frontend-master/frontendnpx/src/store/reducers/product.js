import * as actionTypes from '../actions/actionTypes';
import { updatedObject } from '../../shared/utility';
import { act } from 'react-dom/test-utils';

const initialState = {
    error: null,
    errorStatus: null,
    loading: false,
    products: null,
    productDetails: null,
    responseStatus: null,
    sellerProducts: [],//used for a seller to view his own all products
    sellerProductVariations: [],
    productWithVariations:[],//Storing product details alongwith its variation details
    searchResult:[]


}
const productStart = (state, action) => {
    return updatedObject(state, { error: null, responseStatus: null, loading: true })
}

const productFail = (state, action) => {
    return updatedObject(state, {
        error: action.error,
        responseStatus: null,
        errorStatus: action.errorStatus, loading: false
    })
}

const viewAllProductsSucc = (state, action) => {
    return updatedObject(state, {
        products:action.products,
        error: null,
        loading: false,
    });
};
const viewProductDetailsSucc = (state, action) => {
    return updatedObject(state, {
        productDetails: action.productDetails,
        error: null,
        loading: false,
    });
};
const addProductSucc = (state, action) => {
    return updatedObject(state, {
        responseStatus: action.responseStatus,
        error: null,
        loading: false,
    });
};
const viewAllProductsSellerSucc = (state, action) => {
    return updatedObject(state, {
        sellerProducts: action.sellerProducts,
        error: null,
        loading: false,
    });
};
const deleteProductSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const viewProductVariationsSucc = (state, action) => {
    return updatedObject(state, {
        sellerProductVariations:action.sellerProductVariations,
        error: null,
        loading: false,
    });
};
const updateProductSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const addProductVariationSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const updateProductVariationSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const addToCartSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const deleteOrderSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const viewProductSucc = (state, action) => {
    return updatedObject(state, {
        productWithVariations:action.productWithVariations,
        error: null,
        loading: false,
    });
};
const searchProductSucc = (state, action) => {
    console.log('action',action.searchResult)
    return updatedObject(state, {
        searchResult:action.searchResult,
        error: null,
        loading: false,
    });
};
const productReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.PRODUCT_START: return productStart(state, action);
        case actionTypes.PRODUCT_FAIL: return productFail(state, action);
        case actionTypes.VIEW_ALL_PRODUCTS_SUCCESS: return viewAllProductsSucc(state, action);
        case actionTypes.VIEW_PRODUCT_DETAILS_SUCCESS: return viewProductDetailsSucc(state, action);
        case actionTypes.ADD_PRODUCT_SUCCESS: return addProductSucc(state, action);
        case actionTypes.VIEW_ALL_PRODUCTS_SELLER_SUCCESS: return viewAllProductsSellerSucc(state, action);
        case actionTypes.DELETE_PRODUCT_SUCCESS: return deleteProductSucc(state, action);
        case actionTypes.VIEW_PRODUCT_VARIATIONS_SUCCESS: return viewProductVariationsSucc(state, action);
        case actionTypes.UPDATE_PRODUCT_SUCCESS: return updateProductSucc(state, action);
        case actionTypes.ADD_PRODUCT_VARIATION_SUCCESS: return addProductVariationSucc(state, action);
        case actionTypes.UPDATE_PRODUCT_VARIATION_SUCCESS: return updateProductVariationSucc(state, action);
        case actionTypes.ADD_TO_CART_SUCCESS: return addToCartSucc(state, action);
        case actionTypes.DELETE_ORDER_SUCCESS: return deleteOrderSucc(state, action);
        case actionTypes.VIEW_PRODUCT_SUCCESS: return viewProductSucc(state, action);
        case actionTypes.SEARCH_PRODUCT_SUCCESS: return searchProductSucc(state, action);
        default: return state
    }
}

export default productReducer;