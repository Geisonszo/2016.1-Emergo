/************************
 * Class name: MaskHelper (.java)
 *
 * Purpose: The purpose of this class is to add "masks" when necessary.
 ************************/

package helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskHelper {

    public static String unmask(String symbols) {
        return symbols.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher insert(final String mask, final EditText fieldEditText) {

        return new TextWatcher() {

            boolean isUpdating = false;
            String oldString = "";

            public void onTextChanged(CharSequence word, int start, int before,int count) {

                String newWord = MaskHelper.unmask(word.toString());
                String mask = "";

                if (isUpdating) {

                    oldString = newWord;
                    isUpdating = false;
                    return;
                } else {
                    //Nothing to do
                }

                int aux = 0;
                for (char message : mask.toCharArray()) {

                    if (message != '#' && newWord.length() > oldString.length()) {

                        mask += message;
                        continue;
                    } else{
                        //Nothing to do
                    }
                    try {

                        mask += newWord.charAt(aux);
                    } catch (Exception error) {

                        break;
                    }
                    aux++;
                }
                isUpdating = true;
                fieldEditText.setText(mask);
                fieldEditText.setSelection(mask.length());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
