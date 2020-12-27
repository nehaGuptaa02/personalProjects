import React, { Component } from 'react';
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
    logInLink: {
        color: '#563f46',
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
        margin: '0px 150px'
        // display:'inline-blocks'
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

class UpdateSellerProfile extends Component {

    constructor(props) {
        super(props)

        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            phoneNumber: '',
            companyName: '',
            gst: '',
            image: '',
            loading: false
        }
        this.handleChange = this.handleChange.bind(this)
        this.updateSellerProfileClicked = this.updateSellerProfileClicked.bind(this)
        this.uploadImage = this.uploadImage.bind(this)
    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    updateSellerProfileClicked = () => {
        const token = localStorage.getItem('token');
        this.props.onUpdateSellerProfile(this.state.firstName, this.state.lastName, this.state.email,
            this.state.phoneNumber, this.state.companyName, this.state.gst, token);
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
        this.setState({ image: file.secure_url });
        this.setState({ loading: false })
        const token = localStorage.getItem('token');
        this.props.onImageUpload(this.state.image, token);
    }
    render() {
        const { classes } = this.props;
        let form = (
            <Container component="main" maxWidth="xs">
                <div className={classes.image}>
                    {/* <img alt="loading" width="195" height="145" /> */}
                    {this.state.loading ? (<Spinner/>) : (<img src={this.state.image} alt="loading" width="195" height="145" />)}

                </div>
                <input
                    style={{ display: 'none' }}
                    type="file"
                    name="file"
                    onChange={this.uploadImage}
                    ref={fileInput => this.fileInput = fileInput}
                />
                <button
                    className={classes.button}
                    onClick={() => this.fileInput.click()}>Pick file</button>

                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Update Customer Profile</Typography>
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
                                    label="First Name"
                                    autoFocus
                                />
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
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    onChange={this.handleChange}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="gst"
                                    label="Gst"
                                    name="gst"

                                />
                            </Grid>
                        </Grid>
                        <Button
                            className={classes.submit}
                            onClick={this.updateSellerProfileClicked}
                            type="submit"
                            fullWidth
                            variant="contained"
                        >
                            Update Profile
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
        // loading:state.customer.loading
    };
}
const mapDispatchToProps = dispatch => {
    return {
        onUpdateSellerProfile: (firstName, lastName, email, phoneNumber, companyName, gst, token) => dispatch(actionCreators.updateSellerProfile(firstName, lastName, email, phoneNumber, companyName, gst, token)),
        onImageUpload: (imageUrl,token) => dispatch(actionCreators.imageUpload(imageUrl,token))

    };
}
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(UpdateSellerProfile))