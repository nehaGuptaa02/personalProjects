import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../store/actions/index';
import Spinner from '../../../components/UI/Spinner/Spinner';

const Copyright = () => {
    return (
        <Typography variant="body2" align="center">
            {'Copyright © '}
            <Link color="inherit" href="/">
                E-CommerceProject
      </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

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
    signUpLink: {
        marginRight: theme.spacing(4),
        color: '#563f46',
    },
    logInLink: {
        color: '#563f46',
    },
});

class SignUpSeller extends Component {

    constructor(props) {
        super(props)

        this.state = {
            firstName: '',
            lastName: '',
            companyName: '',
            companyContact: '',
            email: '',
            GST: '',
            password: '',
            confirmPassword: '',
            firstNameError: '',
            lastNameError: '',
            companyNameError: '',
            companyContactError: '',
            emailError: '',
            GSTError: '',
            passwordError: '',
            confirmPasswordError: ''
        }

        this.handleChange = this.handleChange.bind(this)
        this.signUpClicked = this.signUpClicked.bind(this)
    }

    validate = () => {
        let firstNameError = '';
        let lastNameError = '';
        let companyNameError = '';
        let companyContactError = '';
        let emailError = '';
        let GSTError = '';
        let passwordError = '';
        let confirmPasswordError = '';
        if (!this.state.firstName) {
            firstNameError = "First Name cannot be blank";
        }
        if (!this.state.lastName) {
            lastNameError = "Last Name cannot be blank";
        }

        if (!this.state.companyName) {
            companyNameError = "Company Name cannot be blank";
        }
        if (!this.state.email.includes("@")) {
            emailError = "Invalid email";
        }
        if (!this.state.GST) {
            GSTError = "GST cannot be blank";
        }
        if (!this.state.companyContact) {
            companyContactError = "Company Contact cannot be blank";
        }
        if (!this.state.password) {
            passwordError = "Password cannot be blank";
        }
        if (!this.state.confirmPassword) {
            confirmPasswordError = "Confirm Password cannot be blank";
        }
        if (firstNameError || lastNameError || emailError || GSTError ||
            companyContactError || companyNameError || passwordError || confirmPasswordError) {
            this.setState({
                firstNameError, lastNameError, emailError, GSTError,
                companyContactError, companyNameError, passwordError, confirmPasswordError
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
    signUpClicked = (e) => {
        e.preventDefault();
        this.setState({
            firstNameError: '', lastNameError: '', emailError: '', GSTError: '',
            companyContactError: '', companyNameError: '', passwordError: '', confirmPasswordError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            this.props.onSignUpSeller(this.state.firstName, this.state.lastName, this.state.companyName,
                this.state.companyContact, this.state.email, this.state.GST,
                this.state.password, this.state.confirmPassword)
        } // if (!this.props.loading) {
        //     this.props.history.push('/activateAccount');
        // }

    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
                {this.props.responseStatus == 200 ? this.props.history.push('/activateAccount') : null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign up as a Seller
        </Typography>
                    <form className={classes.form} noValidate>
                        {/* //Here the container value is true so it will have the flex container behaviour */}
                        <Grid container spacing={2}>
                            {/* //Here the container value is true so it will have the flex item behaviour */}
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    autoComplete="fname"//autofill
                                    name="firstName"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="firstName"
                                    label="First Name of Seller"
                                    autoFocus
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.firstNameError}
                                </div>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="lastName"
                                    label="Last Name of Seller"
                                    name="lastName"
                                    autoComplete="lname"
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.lastNameError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="companyName"
                                    label="Company Name"
                                    name="companyName"

                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.companyNameError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="companyContact"
                                    label="Company Contact"
                                    name="companyContact" />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.companyContactError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="gst"
                                    label="GST"
                                    name="GST" />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.GSTError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="email"
                                    label="Email Address"
                                    name="email"
                                    autoComplete="email"
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.emailError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="current-password"
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.passwordError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="confirmPassword"
                                    label="Confirm Password"
                                    type="password"
                                    id="confirmPassword"
                                    autoComplete="current-password"
                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.confirmPasswordError}
                                </div>
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    control={<Checkbox value="allowExtraEmails" />}
                                    label="I want to receive inspiration, marketing promotions and updates via email."
                                />
                            </Grid>
                        </Grid>
                        <Button
                            onClick={(e) => this.signUpClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                            className={classes.submit}
                        >
                            Sign Up
                       </Button>
                        <Grid container justify="flex-end">
                            <Grid item>
                                < Link className={classes.signUpLink} href="/signUpCustomer" variant="body2">
                                    Sign Up as a Customer
          </Link>
                            </Grid>
                            <Grid item>
                                < Link className={classes.logInLink} href="/signin" variant="body2">
                                    Already have an account? Sign in
              </Link>
                            </Grid>
                        </Grid>
                    </form>
                </div>
                <Box mt={5}>
                    <Copyright />
                </Box>
            </Container>

        );
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
        loading: state.auth.loading,
        responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onSignUpSeller: (firstName, lastName, companyName, companyContact, email, GST, password, confirmPassword) => dispatch(actionCreators.signUpSeller(firstName, lastName, companyName, companyContact, email, GST, password, confirmPassword))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(SignUpSeller));