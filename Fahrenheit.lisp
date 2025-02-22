;; Convertidor de Fahrenheit a Celsius

(defun fahrenheit-a-celsius (f)
  "Convierte grados Fahrenheit a Celsius."
  (/ (* (- f 32) 5) 9))

(format t "212°F en Celsius es: ~A~%" (fahrenheit-a-celsius 212))
