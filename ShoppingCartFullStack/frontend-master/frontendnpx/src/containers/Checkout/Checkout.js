import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import Grid from '@material-ui/core/Grid';
import Link from '@material-ui/core/Link';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import React, { Component } from 'react';
import FormHelperText from '@material-ui/core/FormHelperText';
import { connect } from 'react-redux';
import { toast } from 'react-toastify';
import Spinner from '../../components/UI/Spinner/Spinner';

const useStyles = theme => ({
    paper: {
        marginTop: theme.spacing(15),
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
    formControl: {
        marginTop: theme.spacing(3),

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

class Checkout extends Component {

    constructor(props) {
        super(props)

        this.state = {
            email: '',
            phoneNumber: '',
            emailError: '',
            phoneNumberError: '',
            cash: false,
            online: false,
            checkboxError: ''

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
        debugger;
        let emailError = '';
        let phoneNumberError = '';
        let checkboxError = '';
        if (!this.state.email.includes("@")) {
            emailError = "Invalid email";
        }
        if (!this.state.phoneNumber || this.state.phoneNumber.length < 10) {
            phoneNumberError = "Invalid Phone number";
        }
        if (this.state.cash === false && this.state.online === false) {
            checkboxError = "Please select a mode of payment";
        }
        // if (this.state.cash === true && this.state.online === true) {
        //     checkboxError = "Please one  mode of payment";
        // }

        if (emailError || phoneNumberError || checkboxError) {
            this.setState({
                emailError, phoneNumberError, checkboxError
            });
            return false;
        }

        return true;
    };
    signUpClicked = (e) => {
        e.preventDefault();
        this.setState({
            emailError: '',
            phoneNumberError: '',
            checkboxError: ''
        })
        const isValid = this.validate();
        if (isValid) {
            toast.success('Your order have placed.Details are send to your mail', {
                position: toast.POSITION.BOTTOM_LEFT,
                autoClose: 5000
            })
            this.props.history.push('/')
        }


    }
    render() {
        const { classes } = this.props;
        const { cash, online } = this.setState;
        let form = (
            <Container component="main" maxWidth="xs">
                {this.props.responseStatus == 200 ? this.props.history.push('/activateAccount') : null}
                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Checkout  </Typography>
                    <form className={classes.form} noValidate>
                        {/* //Here the container value is true so it will have the flex container behaviour */}
                        <Grid container spacing={2}>
                            {/* //Here the container value is true so it will have the flex item behaviour */}

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
                        </Grid>
                        <FormControl component="fieldset" className={classes.formControl}>
                            <FormLabel component="legend">Check Payment method</FormLabel>
                            <FormGroup>
                                <FormControlLabel
                                    control={<Checkbox checked={cash} onChange={this.handleChange} name="cash" />}
                                    label="Cash On delivery"
                                />
                                <FormControlLabel
                                    control={<Checkbox checked={online} onChange={this.handleChange} name="online" />}
                                    label="Pay Online"
                                />
                            </FormGroup>
                            <FormHelperText>
                                <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.checkboxError}
                                </div>
                            </FormHelperText>
                        </FormControl>
                        <Button
                            className={classes.submit}
                            onClick={(e) => this.signUpClicked(e)}
                            type="button"
                            fullWidth
                            variant="contained"
                        >
                            Place Order
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

    };
}
const mapDispatchToProps = dispatch => {
    return {
    };
}


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(Checkout))