import React, { useEffect, useRef } from 'react';
import { useField } from '@unform/core';
import Checkbox, { } from '@react-native-community/checkbox';
import CheckBox from '@react-native-community/checkbox';

interface InputProps {
    name: string,
    [rest: string]: any;
}

function CheckboxInput({ name, ...rest }: InputProps) {

    const inputRef = useRef<CheckBox>(null);
    const { fieldName, registerField, defaultValue, error } = useField(name);

    useEffect(() => {
        if (inputRef !== null) {
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
                // @ts-ignore: Object is possibly 'null'.
                inputRef.current.value = value;
            },
            getValue(ref) {
                return ref.value;
            },
        });
    }, [fieldName, registerField]);

    return (
        <CheckBox
            ref={inputRef}
            value={defaultValue}
            onValueChange={value => {
                console.log(value);
                // @ts-ignore: Object is possibly 'null'.
                inputRef.current.value = value;

            }}
            {...rest}
        />
    );
};
export default CheckboxInput;