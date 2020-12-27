import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../store/actions/index';
import Spinner from '../../../components/UI/Spinner/Spinner';
import LocationOnIcon from '@material-ui/icons/LocationOn';

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
    logInLink: {
        color: '#563f46',
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

class AddAddress extends Component {

    constructor(props) {
        super(props)

        this.state = {
            city: '',
            state: '',
            country: '',
            zipCode: '',
            label: '',
            addressLine: '',
            cityError: '',
            stateError: '',
            countryError: '',
            zipCodeError: '',
            // labelError: '',
            addressLineError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.addAddressClicked = this.addAddressClicked.bind(this)
    }
    validate = () => {
        let cityError = '';
        let stateError = '';
        let countryError = '';
        let zipCodeError = '';
        // let labelError='';
        let addressLineError = '';
        if (!this.state.city) {
            cityError = "City cannot be blank";
        }
        if (!this.state.state) {
            stateError = "State cannot be blank";
        }

        if (!this.state.country) {
            countryError = "Country cannot be blank";
        }
        if (!this.state.zipCode) {
            zipCodeError = "Zip Code cannot be blank";
        }
        // if (!this.state.label) {
        //     labelError = "La cannot be blank";
        // }
        if (!this.state.addressLine) {
            addressLineError = "Address  Line cannot be blank";
        }

        if (cityError || stateError || countryError ||
            zipCodeError || addressLineError) {
            this.setState({
                cityError, stateError, countryError,
                zipCodeError, addressLineError
            });
            return false;
        }

        return true;
    };
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    addAddressClicked = (e) => {
        e.preventDefault();
        this.setState({
            cityError: '', stateError: '', countryError: '',
            zipCodeError: '', addressLineError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            const token = localStorage.getItem('token');
            const type = localStorage.getItem('type');
            this.props.onAddAddress(this.state.city, this.state.state, this.state.country, this.state.zipCode, this.state.label, this.state.addressLine, token, type)
        }
    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LocationOnIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Add Address
    </Typography>
                    <form className={classes.form} noValidate>
                        {/* //Here the container value is true so it will have the flex container behaviour */}
                        <Grid container spacing={2}>
                            {/* //Here the container value is true so it will have the flex item behaviour */}
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    name="city"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="city"
                                    label="City"
                                    autoFocus
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.cityError}
                                </div>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="state"
                                    label="State"
                                    name="state"
                                />
                                 <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.countryError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="country"
                                    label="Country"
                                    name="country"

                                />
                                 <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.stateError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="zipcode"
                                    label="Zip Code"
                                    name="zipCode"
                                    autoComplete="zipCode"
                                />
                                 <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.zipCodeError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    // required
                                    fullWidth
                                    name="label"
                                    label="Label"
                                    id="label"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="addressLine"
                                    label="Address Line"
                                    id="addressLine"

                                />
                                 <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.addressLineError}
                                </div>
                            </Grid>
                        </Grid>
                        <Button
                            className={classes.submit}
                            // onSubmit={(e) => {this.addAddressClicked(e)}}
                            onClick={(e)=>this.addAddressClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        >Add Address</Button>
                    </form>
                </div>
                {/* The Box component wraps your component. 
            It creates a new DOM element, a <div> by default
             that can be changed with the component property. */}
                <Box mt={5}>
                    <Copyright />
                </Box>
            </Container>);

        //}
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
        loading: state.customer.loading
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onAddAddress: (city, state, country, zipCode, label, addressLine, token, type) => dispatch(actionCreators.addAddress(city, state, country, zipCode, label, addressLine, token, type))
    };
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(AddAddress))