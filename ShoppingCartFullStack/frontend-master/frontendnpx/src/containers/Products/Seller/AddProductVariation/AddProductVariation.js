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

class AddProductVariation extends Component {

    constructor(props) {
        super(props)

        this.state = {
            quantityAvailable: '',
            price: '',
            primaryImageName: '',
            metadataKey: '',
            metadataValue: '',
            quantityAvailableError: '',
            priceError: '',
            primaryImageNameError: '',
            metadataKeyError: '',
            metadataValueError: '',
            loading: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.addProductVariationClicked = this.addProductVariationClicked.bind(this)
    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    validate = () => {
        let quantityAvailableError = '';
        let priceError = '';
        let primaryImageNameError = '';
        let metadataKeyError = '';
        let metadataValueError = '';

      
        if (!this.state.quantityAvailable) {
            quantityAvailableError = "Quantity Available cannot be blank.";
        }
        if (!this.state.price) {
            priceError = "Price cannot be blank.";
        }
       
        if (!this.state.primaryImageName) {
            primaryImageNameError = "Primary Image cannot be blank.";
        }
        if (!this.state.metadataKey) {
            metadataKeyError = "MetadataKey cannot be blank.";
        }
        if (!this.state.metadataValue) {
            metadataValueError = "MetadataValue cannot be blank.";
        }
        if (quantityAvailableError || priceError  || primaryImageNameError || metadataKeyError || metadataValueError) {
            this.setState({
                 quantityAvailableError, priceError, primaryImageNameError, metadataKeyError, metadataValueError
            });
            return false;
        }

        return true;
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
    addProductVariationClicked = (e) => {
        console.log('Produt variation clicked')
        e.preventDefault();
        this.setState({
             quantityAvailableError: '', priceError: '', categoryIdError: '',
            primaryImageNameError: '', metadataKeyError: '', metadataValueError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            const token = localStorage.getItem('token');
            this.props.onAddProductVariation(this.props.location.state.productId, this.state.quantityAvailable,
                this.state.price, this.state.primaryImageName, this.state.metadataKey,
                this.state.metadataValue, token);
        }


    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
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
                <div style={{ fontSize: 16, color: '#563f46',textAlign:'center' }}>
                    {this.state.primaryImageNameError}
                </div>
                <button
                    className={classes.button}
                    onClick={() => this.fileInput.click()}>Pick file</button>
                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Add Product Variation </Typography>
                    <form className={classes.form}>
                        <Grid container spacing={2}>
                           
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
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.quantityAvailableError}
                                </div>
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
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.priceError}
                                </div>
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
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.metadataKeyError}
                                </div>
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
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.metadataValueError}
                                </div>
                            </Grid>
                        </Grid>
                        <Button
                            className={classes.submit}
                            onClick={(e) => this.addProductVariationClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        > Add Product
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
        onAddProductVariation: (productId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token) => dispatch(actionCreators.addProductVariation(productId, quantityAvailable, price, primaryImageName, metadataKey, metadataValue, token))
    };
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(AddProductVariation))