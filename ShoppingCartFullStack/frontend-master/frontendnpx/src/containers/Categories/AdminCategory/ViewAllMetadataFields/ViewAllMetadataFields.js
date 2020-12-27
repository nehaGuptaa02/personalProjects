import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import styles from './ViewAllMetadataFields.module.css';
// import { Pagination } from 'react-bootstrap';
class ViewAllMetadataFields extends Component {
    componentDidMount() {
        const token = localStorage.getItem('token');
        this.props.onViewAllMetadataFields(token);
    }
    render() {
        let metadataList = this.props.allMetadataFields.map(metadata => (
            <div key={metadata.id} className={styles.Div}>
                <p>ID: {metadata.id}</p>
                <p>NAME: {metadata.name}</p>
            </div>
        ))

        return (
            <div>
                <h2 style={{ color: '#563f46', textAlign: 'center' }}>List of all Metadata</h2>
                {/* <Pagination
                    bsSize="medium"
                    items={10}
                    activePage={1}/> */}
                {metadataList}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        allMetadataFields: state.category.allMetadataFields
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onViewAllMetadataFields: (token) => dispatch(actionCreators.viewAllMetadataFields(token)),
    };
}

export default connect(mapStateToProps,mapDispatchToProps)(ViewAllMetadataFields);