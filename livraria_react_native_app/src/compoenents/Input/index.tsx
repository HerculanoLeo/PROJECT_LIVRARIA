import React, { useEffect, useRef } from 'react';
import { useField } from '@unform/core';
import { TextInput } from 'react-native';

interface InputProps {
    name: string,
    [rest: string]: any;
}

function Input({ name, ...rest }: InputProps) {

    const inputRef = useRef<TextInput>(null);
    const { fieldName, registerField, defaultValue, error } = useField(name);

    useEffect(() => {
        if(inputRef !== null) {
             // @ts-ignore: Object is possibly 'null'.
            inputRef.current.value = defaultValue;
        }
    }, [defaultValue]);

    useEffect(() => {
        registerField({
            name: fieldName,
            ref: inputRef.current,
            clearValue(ref) {
                ref.value = '';
                ref.clear();
            },
            setValue(ref, value) {
                ref.setNativeProps({ text: value });
                if(inputRef !== null) {
                    // @ts-ignore: Object is possibly 'null'.
                   inputRef.current.value = value;
                }
            },
            getValue(ref) {
                return ref.value;
            },
        });
    }, [fieldName, registerField]);

    return (
        <TextInput
            ref={inputRef}
            keyboardAppearance="dark"
            defaultValue={defaultValue}
            placeholderTextColor="#666360"
            onChangeText={value => {
                if(inputRef !== null) {
                    // @ts-ignore: Object is possibly 'null'.
                   inputRef.current.value = value;
                }
            }}
            {...rest}
        />
    );
};
export default Input;