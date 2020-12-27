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
import { updateObject } from '../../../shared/utility';
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
    },
    signUpLink:{
    marginRight:theme.spacing(7),
    color: '#563f46',
    },
    notchedOutline: {
        borderWidth: "10px",
        borderColor: "#563f46"
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

class SignUpCustomer extends Component {

    constructor(props) {
        super(props)

        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            phoneNumber: '',
            password: '',
            confirmPassword: '',
            firstNameError: '',
            lastNameError: '',
            emailError: '',
            phoneNumberError: '',
            passwordError: '',
            confirmPasswordError: '',

        }

        this.handleChange = this.handleChange.bind(this)
        this.signUpClicked = this.signUpClicked.bind(this)
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
        let firstNameError = '';
        let lastNameError = '';
        let emailError = '';
        let phoneNumberError = '';
        let passwordError = '';
        let confirmPasswordError = '';
        if (!this.state.firstName) {
            firstNameError = "First Name cannot be blank";
        }
        if (!this.state.lastName) {
            lastNameError = "Last Name cannot be blank";
        }

        if (!this.state.email.includes("@")) {
            emailError = "Invalid email";
        }
        if (!this.state.phoneNumber) {
            phoneNumberError = "Phone Number cannot be blank";
        }
        if (!this.state.password) {
            passwordError = "Password cannot be blank";
        }
        if (!this.state.confirmPassword) {
            confirmPasswordError = "Confirm Password cannot be blank";
        }

        if (firstNameError || lastNameError || emailError ||
            phoneNumberError || passwordError || confirmPasswordError) {
            this.setState({
                firstNameError, lastNameError, emailError,
                phoneNumberError, passwordError, confirmPasswordError
            });
            return false;
        }

        return true;
    };
    signUpClicked = (e) => {
        e.preventDefault();
        this.setState({
            firstNameError: '', lastNameError: '', emailError: '',
            phoneNumberError: '', passwordError: '', confirmPasswordError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            this.props.onSignUpCustomer(this.state.firstName, this.state.lastName, this.state.email,
                this.state.phoneNumber, this.state.password, this.state.confirmPassword);

        }


    }
    render() {
        const { classes } = this.props;
        // if (!this.props.loading) {
        let form = (
            <Container component="main" maxWidth="xs">
            {this.props.responseStatus == 200 ? this.props.history.push('/activateAccount'):null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign up Customer
    </Typography>
                    <form className={classes.form} noValidate>
                        {/* //Here the container value is true so it will have the flex container behaviour */}
                        <Grid container spacing={2}>
                            {/* //Here the container value is true so it will have the flex item behaviour */}
                            <Grid item xs={12} sm={6}>
                                <TextField

                                    classes={{ root: classes.notchedOutline }}
                                    onChange={this.handleChange}
                                    autoComplete="fname"//autofill
                                    name="firstName"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
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
                                    label="Last Name"
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
                                    id="phoneNumber"
                                    label="Phone Number"
                                    name="phoneNumber"

                                />
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.phoneNumberError}
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
                            className={classes.submit}
                            onClick={(e) => this.signUpClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        >
                            Next
                   </Button>
                        <Grid container justify="flex-end">
                        <Grid item>
                                < Link className={classes.signUpLink} href="/signUpSeller" variant="body2">
                                    Sign Up as a Seller
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
        loading: state.auth.loading,
        responseStatus: state.auth.responseStatus
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onSignUpCustomer: (firstName, lastName, email, phoneNumber, password, confirmPassword) => dispatch(actionCreators.signUpCustomer(firstName, lastName, email, phoneNumber, password, confirmPassword))
    };
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(SignUpCustomer))