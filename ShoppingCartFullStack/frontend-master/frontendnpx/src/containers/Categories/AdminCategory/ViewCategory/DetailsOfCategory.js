import React, { Component } from 'react';
import { connect } from 'react-redux';
import styles from './DetailsOfCategory.module.css';
// import { Pagination } from 'react-bootstrap';
class DetailsOfCategory extends Component {
    render() {
        let subCategoryList = null;
        if (this.props.subCategories) {
            subCategoryList = this.props.subCategories.map(subCategory => (
                <div key={subCategory.id} className={styles.Div}>
                    <p>ID: {subCategory.id}</p>
                    <p>SUBCATEGORY NAME: {subCategory.name}</p>
                </div>
            ))
            // let metadataValuesList = null;
            // if (this.props.categoryMetadataFieldDtoSet) {
            //     metadataValuesList = this.props.categoryMetadataFieldDtoSet.map(value => (
                   
            //             <div key={value.id} className={styles.Div}>
            //                 <p>ID: {value.id}</p>
            //                 <p>TYPE: {value.name}</p>
            //             </div>
                    

            //     ))

            // }
            // let SubCategoryHeading=null;
            // if(this.props.subCategories!=null)
            // {
            //     SubCategoryHeading=(
            //         <strong><h3 style={{ color: '#563f46', textAlign: 'center' }}>
            //         The Sub Categories assosiated with category Id are the following :</h3></strong>
            //     );
            // }
            return (
                <div>
                    <strong><h1 style={{ color: '#563f46', textAlign: 'center' }}>
                        The category  assosiated with category Id {this.props.categoryDto.id} is {this.props.categoryDto.name} </h1></strong>
                    {/* {SubCategoryHeading} */}
                    {subCategoryList}
                    {/* <strong><h3 style={{ color: '#563f46', textAlign: 'center' }}>
                        Folllowing are the metadata Values :</h3></strong> */}
                </div>
            );
        }

    }
}
const mapStateToProps = state => {
    return {
        categoryDto: state.category.categoryDto,
        subCategories: state.category.subCategories,
        categoryMetadataFieldDtoSet: state.category.categoryMetadataFieldDtoSet
    };
}

export default connect(mapStateToProps)(DetailsOfCategory);