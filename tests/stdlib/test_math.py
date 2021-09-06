from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):

    def test_sqrt(self):
        def create_test(arg):
            return f"""
                import math
                try:
                    print(math.sqrt({arg}))
                except Exception as e:
                    print(type(e), ':', e)
            """

        self.assertCodeExecution(create_test("4"))
        self.assertCodeExecution(create_test("12.34"))
        self.assertCodeExecution(create_test("-3.5"))
        self.assertCodeExecution(create_test("-0.0"))
        self.assertCodeExecution(create_test("float('inf')"))
        self.assertCodeExecution(create_test("float('nan')"))
        self.assertCodeExecution(create_test("float('-inf')"))
        self.assertCodeExecution(create_test("'Hello'"))
