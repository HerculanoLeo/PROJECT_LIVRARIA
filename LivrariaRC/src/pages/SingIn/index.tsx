import React, { useRef, useState } from 'react';
import { StyleSheet, Text, View, Image } from 'react-native';
import { Form } from '@unform/mobile';
import { FormHandles, SubmitHandler } from '@unform/core';
import { RectButton, TouchableOpacity } from 'react-native-gesture-handler';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons'
import CheckBox from '@react-native-community/checkbox';

import Input from '../../compoenents/Input';
import bookImage from '../../images/bookImages.png';

interface FormData {
  name: string;
  email: string;
};

export default function SignIn() {

  const formRef = useRef<FormHandles>(null);

  const [toggleCheckbox, setToggleCheckbox] = useState(false);
  const [secureTextEntry, setSecureTextEntry] = useState(true);

  const handleSubmit: SubmitHandler<FormData> = (data) => {
    console.log(data, "lembreMe:", toggleCheckbox);
  };

  const handleSecureText = () => {
    setSecureTextEntry(!secureTextEntry);
  }


  return (
    <View style={styles.container}>

      <View style={styles.imgView}>
        <Image source={bookImage} style={styles.bookImage} />
      </View>

      <Form ref={formRef} onSubmit={handleSubmit} style={styles.formView}>

        <Text style={styles.title}>Fazer Login</Text>

        <Text>E-mail</Text>
        <View style={styles.viewInput}>
          <Input style={styles.input} name="email" type="email" />
        </View>

        <Text>Senha</Text>
        <View style={styles.viewInput}>
          <Input style={styles.input} name="password" secureTextEntry={secureTextEntry} />
          <TouchableOpacity onPress={handleSecureText}>
            <FontAwesomeIcon icon={secureTextEntry ? faEye : faEyeSlash} color="#D9DCDE" size={24} />
          </TouchableOpacity>
        </View>

        <View style={styles.viewButton}>
          
          <View style={styles.viewLembreMe}>
            <CheckBox
              disabled={false}
              onAnimationType="fill"
              offAnimationType='fill'
              tintColor='#000'
              onFillColor="#FFF"
              boxType='square'
              value={toggleCheckbox}
              onValueChange={setToggleCheckbox}
            />
            <Text style={styles.textLembreMe}>Lembre Me</Text>
          </View>

          <TouchableOpacity style={styles.viewEsqueciSenha} onPress={() => { }}>
            <Text>Esqueci a senha</Text>
          </TouchableOpacity>

        </View>

        <View style={styles.viewButtonEntrar}>
          <RectButton style={styles.buttonEntrar} onPress={() => {
            if (formRef !== null) {
              // @ts-ignore: Object is possibly 'null'.
              formRef.current.submitForm();
            }
          }}>
            <Text style={styles.textEntrar}>Entrar</Text>
          </RectButton>
        </View>
      
      </Form>

    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: "center",
    alignItems: 'stretch',
    marginHorizontal: 15,
  },

  imgView: {
    marginTop: -60,
    marginBottom: 20,
    flexDirection: 'column',
    alignItems: 'center',
    alignSelf: "center",
    justifyContent: "center",
    height: 180,
    width: 180,
  },

  bookImage: {
    height: 180,
    width: 180,
  },

  formView: {
    flexDirection: 'column',
    alignItems: "flex-start",
    justifyContent: 'center',
  },

  title: {
    fontSize: 32,
    fontWeight: '700',
    marginBottom: 24
  },

  viewInput: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    height: 45,
    marginVertical: 5,
    borderRadius: 8,
    borderWidth: 0,
    paddingHorizontal: 10,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 10,
    // background color must be set
    backgroundColor: "#FFF" // invisible
  },

  checkbox: {
    backgroundColor: '#FFF',
  },

  input: {
    flex: 1
  },

  viewButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginVertical: 5,
    
  },

  viewLembreMe: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
  },

  textLembreMe: {
    marginLeft: 5,
    fontWeight: "600",
  },

  viewEsqueciSenha: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },

  textEsqueciSenha: {
    fontSize: 12,
    fontWeight: "600",
    color: "#8FA7B2"
  },

  viewButtonEntrar: {
    flexDirection: 'row',
  },

  buttonEntrar: {
    flex: 1,
    height: 45,
    marginVertical: 10,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 10,
    // background color must be set
    backgroundColor: "#1EC6F3" // invisible
  },

  textEntrar: {
    color: "#FFF",
    fontSize: 20,
    fontWeight: "700"
  }

})