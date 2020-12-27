import * as actionTypes from '../actions/actionTypes';
import { updatedObject } from '../../shared/utility';

const initialState = {
    error: null,
    errorStatus: null,
    loading: false,
    responseStaus: null,
    allMetadataFields: [],
    categoryDto: [],
    subCategories: [],
    categoryMetadataFieldDtoSet: [],
    allCategories:[]

}
const categoryStart = (state, action) => {
    return updatedObject(state, { error: null, loading: true })
}
const categoryAddMetadataFieldSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false
    });
};
const categoryFail = (state, action) => {
    return updatedObject(state, { error: action.error, errorStatus: action.errorStatus, loading: false })
}
const categoryViewAllMetadataFieldsSucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
        allMetadataFields: action.allMetadataFields
    });
};
const addCategorySucc = (state, action) => {
    return updatedObject(state, {
        allCategories:action.allCategories,
        error: null,
        loading: false,
    });
};
const viewCategorySucc = (state, action) => {
    return updatedObject(state, {
        categoryDto: action.categoryDto,
        subCategories: action.subCategories,
        categoryMetadataFieldDtoSet: action.categoryMetadataFieldDtoSet,
        error: null,
        loading: false,
    });
};
const viewAllCategoriesSucc = (state, action) => {
    return updatedObject(state, {
        allCategories:action.allCategories,
        error: null,
        loading: false,
    });
};
const updateCategorySucc = (state, action) => {
    return updatedObject(state, {
        error: null,
        loading: false,
    });
};
const categoryReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.CATEGORY_START: return categoryStart(state, action);
        case actionTypes.CATEGORY_FAIL: return categoryFail(state, action);
        case actionTypes.CATEGORY_ADD_METADATA_FIELD_SUCCESS: return categoryAddMetadataFieldSucc(state, action);
        case actionTypes.CATEGORY_VIEW_ALL_METADATA_FIELDS_SUCCESS: return categoryViewAllMetadataFieldsSucc(state, action);
        case actionTypes.ADD_CATEGORY_SUCCESS: return addCategorySucc(state, action);
        case actionTypes.VIEW_CATEGORY_SUCCESS: return viewCategorySucc(state, action);
        case actionTypes.VIEW_ALL_CATEGORIES_SUCCESS: return viewAllCategoriesSucc(state, action);
        case actionTypes.UPDATE_CATEGORY_SUCCESS: return updateCategorySucc(state, action);


        default: return state
    }
}

export default categoryReducer;