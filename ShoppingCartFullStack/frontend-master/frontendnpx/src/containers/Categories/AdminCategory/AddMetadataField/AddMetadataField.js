import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import * as actionCreators from '../../../../store/actions/index';
import Spinner from '../../../../components/UI/Spinner/Spinner';

const Copyright = () => {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
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
        marginTop: theme.spacing(20),
        marginBottom: theme.spacing(26),
        margin: '20px',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: '#563f46',
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        color: 'white',
        backgroundColor: '#563f46',
        '&:hover': {
            background: '#A29599',
        }
    },
    link: {
        color: '#563f46',
    },
    text: {
        color: '#563f46',
    },
    Checkbox: {
        color: '#563f46',
        backgroundColor: '#563f46',
    }
});

class AddMetadataField extends Component {

    constructor(props) {
        super(props)

        this.state = {
            fieldName: '',
            fieldNameError:''
        }
        this.handleChange = this.handleChange.bind(this)
        this.addMetadataFieldClicked = this.addMetadataFieldClicked.bind(this)
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
        let fieldNameError='';
        if (!this.state.fieldName) {
            fieldNameError = "Field Name of the metadata cannot be blank";
        }
        if (fieldNameError) {
            this.setState({
               fieldNameError
            });
            return false;
        }

        return true;
    };
    addMetadataFieldClicked = (e) => {
        e.preventDefault();
        this.setState({
          fieldNameError:''
        })
        const isValid = this.validate();
        if (isValid) {
        const token =localStorage.getItem('token');
         this.props.onAddMetadataField(this.state.fieldName,token)   
        } 
    }

    render() {
        const { classes } = this.props;
        return (
            <div>
                <Container component="main" maxWidth="xs">
                    <CssBaseline />
                    <div className={classes.paper}>
                        <Typography
                            className={classes.text}
                            component="h1" variant="h5">
                            Add Metadata Field Name
                        </Typography>
                        <form className={classes.form} noValidate>
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="fieldName"
                                name="fieldName"
                                label="Metadata Field Name"
                                autoComplete="fieldName"
                                autoFocus
                                onChange={this.handleChange}
                            />
                             <div style={{ fontSize: 12, color: '#563f46' }}>
                                    {this.state.fieldNameError}
                                </div>
                            <Button
                                onClick={(e)=>this.addMetadataFieldClicked(e)}
                                type="button"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className={classes.submit}
                            >
                                Add metadataField
                      </Button>
                        </form>
                    </div>
                    <Box mt={8}>
                        <Copyright />
                    </Box>
                </Container>
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
        onAddMetadataField:(fieldName,token)=>dispatch(actionCreators.addMetadataField(fieldName,token))
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(useStyles)(AddMetadataField));


