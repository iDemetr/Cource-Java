package Lr_6;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

class NonEditableDocumentFilter extends DocumentFilter {
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
	// Запрещаем удаление символов
	// Ничего не делаем, чтобы удалить символы
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
	    throws BadLocationException {
	// Разрешаем вставку строк
	super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
	    throws BadLocationException {
	// Разрешаем замену текста
	super.replace(fb, offset, length, text, attrs);
    }
}
