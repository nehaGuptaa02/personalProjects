import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import Spinner from '../../../../components/UI/Spinner/Spinner';

const useStyles = theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: '#563f46',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        color: 'white',
        backgroundColor: '#563f46',
        '&:hover': {
            background: '#A29599',
        }
    },
    image: {
        border: '2px solid #563f46',
        alignItems: 'center',
        margin: '40px 100px',
        height: '150px',
        color: '#563f46',
        width: '200px'

    },
    button: {
        color: 'white',
        backgroundColor: '#563f46',
        width: '100px',
        height: '40px',
        fontSize: '20px',
        margin: '5px 150px'
    }
});
const Copyright = () => {
    return (
        <Typography variant="body2" color="inherit" align="center">
            {'Copyright © '}
            <Link color="inherit" href="/">
                E-CommerceProject
      </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

class UpdateProductVariation extends Component {
     constructor(props) {
        super(props)
        this.state = {
            // productVariationId: '',
            quantityAvailable: '',
            price: '',
            primaryImageName: '',
            metadataKey: '',
            metadataValue: '',
            loading: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.updateProductVariationClicked = this.updateProductVariationClicked.bind(this)
    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    uploadImage = async e => {
        const files = e.target.files
        const data = new FormData()
        data.append('file', files[0])
        data.append('upload_preset', 'ShoppingCart')
        this.setState({ loading: true })
        const res = await fetch(
            'https://api.cloudinary.com/v1_1/dbnkpr18k/image/upload',
            {
                method: 'POST',
                body: data
            }
        )
        const file = await res.json()
        this.setState({ primaryImageName: file.secure_url });
        this.setState({ loading: false })
    }
    updateProductVariationClicked = (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        this.props.onUpdateProductVariation(this.props.location.state.variationId, this.state.quantityAvailable,
            this.state.price, this.state.primaryImageName, this.state.metadataKey,
            this.state.metadataValue, token);


    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
                {/* {this.props.responseStatus == 200 ? this.props.history.push('/sellerProduct') : null} */}
                <div className={classes.image}>
                    {this.state.loading ? (<Spinner />) : (<img src={this.state.primaryImageName} alt="loading" width="195" height="145" />)}

                </div>
                <input
                    style={{ display: 'none' }}
                    type="file"
                    name="file"
                    onChange={this.uploadImage}
                    ref={fileInput => this.fileInput = fileInput}
                />
                <div style={{ fontSize: 16, color: '#563f46', textAlign: 'center' }}>
                    {this.state.primaryImageNameError}
                </div>
                <button
                    className={classes.button}
                    onClick={() => this.fileInput.click()}>Pick file</button>
                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Update Product Variation </Typography>
                    <form className={classes.form}>
                        <Grid container spacing={2}>
                            {/* <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    name="name"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="productVariationId"
                                    label="Product Variation Id"
                                    name="productVariationId"
                                    autoFocus
                                />
                            </Grid> */}
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="quantityAvailable"
                                    label="Quantity Available"
                                    name="quantityAvailable"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="price"
                                    label="Price"
                                    name="price"

                                />
                            </Grid>
                          
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    fullWidth
                                    required
                                    name="metadataKey"
                                    label="Metadata Key"
                                    id="metadataKey"

                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    fullWidth
                                    required
                                    name="metadataValue"
                                    label="MetadataValue"
                                    id="metadaValue"
                                />
                            </Grid>
                        </Grid>
                        <Button
                            className={classes.submit}
                            onClick={(e) => this.updateProductVariationClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        > Update Product Variation
                   </Button>
                    </form>
                </div>
                <Box mt={5}>
                    <Copyright />
                </Box>
            </Container>);
        if (this.props.loading) {
            form = <Spinner />
        }

        return (
            <div>
                {form}
            </div>

        );


    }
}
const mapStateToProps = state => {
    return {
        loading: state.product.loading,
        //  responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onUpdateProductVariation: (productVariationId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token) => dispatch(actionCreators.updateProductVariation(productVariationId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(UpdateProductVariation))