package com.example.calculator;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.List;

public class CalculatorViewModel extends ViewModel {
    // Lưu nội dung hiện tại (số hoặc biểu thức)
    private final MutableLiveData<String> currentText = new MutableLiveData<>("0");
    private final MutableLiveData<String> resultText = new MutableLiveData<>("");
    private final MutableLiveData<List<String>> historyList = new MutableLiveData<>(new ArrayList<>());

    private boolean isResultDisplayed = false; // kiểm tra xem vừa mới nhấn "=" chưa
    private double lastResult = 0;
    // Cho phép các Fragment khác (ví dụ Manhinh_tinh) quan sát
    public LiveData<String> getCurrentText() {
        return currentText;
    }
    public LiveData<String> getResultText() {
        return resultText;
    }
    public LiveData<List<String>> getHistoryList()
    {
        return historyList;
    }

    public void clearText() {
        currentText.setValue("0");
        resultText.setValue("");
        isResultDisplayed = false;
        lastResult = 0;
    }
    public void deleteLastChar() {
        String text = currentText.getValue();
        if (text == null || text.isEmpty()) return;

        // ✅ Nếu đang hiển thị kết quả, cho phép quay lại biểu thức cũ để sửa
        if (isResultDisplayed) {
            isResultDisplayed = false;
            resultText.setValue("");
            // Giữ nguyên currentText (ví dụ "5+3") để người dùng sửa
            return;
        }

        if (text.length() == 1) {
            currentText.setValue("0");
        } else {
            currentText.setValue(text.substring(0, text.length() - 1));
        }
    }
    public void appendText(String value) {
        String text = currentText.getValue();
        if (text == null) text = "0";

        // 🧠 Nếu vừa nhấn "="
        if (isResultDisplayed) {
            if (isNumeric(value)) {
                // Nếu nhấn số → bắt đầu phép mới
                text = value;
            } else if (isOperator(value)) {
                // Nếu nhấn toán tử → nối tiếp kết quả cũ
                text = String.valueOf(lastResult) + value;
            }
            else {
                // 👉 Nếu nhấn ký tự khác (ví dụ D, C, ., v.v.) → giữ nguyên
                isResultDisplayed = false;
                return;
            }
            isResultDisplayed = false;
        }else {
            // 🧮 Bình thường (chưa nhấn =)
            if (text.equals("0") && value.matches("[0-9]")) {
                text = value;
            } else {
                text += value;
            }
        }

        currentText.setValue(text);
    }
    public void calculateResult() {
        String expr = currentText.getValue();
        if (expr == null || expr.isEmpty()) return;

        try {
            double result = evaluateWithRhino(expr);
            resultText.setValue(String.valueOf(result));
            lastResult = result;              // ✅ Lưu kết quả
            isResultDisplayed = true;
            addToHistory(expr + " = " + result);// ✅ Đánh dấu đã hiển thị kết quả
        } catch (Exception e) {
            resultText.setValue("Error");
            isResultDisplayed = false;
        }
    }

    // ⚡ Dùng Rhino để tính toán biểu thức
    private double evaluateWithRhino(String expression) {
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1); // BẮT BUỘC cho Android
        try {
            org.mozilla.javascript.Scriptable scope = rhino.initStandardObjects();
            Object result = rhino.evaluateString(scope, expression, "JavaScript", 1, null);
            return Double.parseDouble(result.toString());
        } catch (Exception e) {
            throw new RuntimeException("Invalid Expression: " + expression, e);
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }
    // Kiểm tra có phải toán tử hay không
    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%");
    }

    // Kiểm tra có phải là số hay không
    private boolean isNumeric(String s) {
        return s.matches("\\d");
    }
    private void addToHistory(String entry) {
        List<String> list = historyList.getValue();
        if (list == null) list = new ArrayList<>();
        list.add(0, entry); // thêm lên đầu danh sách
        historyList.setValue(list);
    }
    public void clearHistory() {
        historyList.setValue(new ArrayList<>());
    }

}
