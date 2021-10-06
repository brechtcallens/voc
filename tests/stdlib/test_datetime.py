from ..utils import TranspileTestCase


class DatetimeModuleTests(TranspileTestCase):

    def test_date_set_year_exception(self):
        self.assertCodeExecution("""
            from datetime import date
            try:
                d = date(1970, 1, 1)
                d.year = 1971
            except Exception as e:
                print(type(e), ':', e)
            """)
    
    def test_date_set_month_exception(self):
        self.assertCodeExecution("""
            from datetime import date
            try:
                d = date(1970, 1, 1)
                d.month = 2
            except Exception as e:
                print(type(e), ':', e)
            """)
    
    def test_date_set_day_exception(self):
        self.assertCodeExecution("""
            from datetime import date
            try:
                d = date(1970, 1, 1)
                d.day = 2
            except Exception as e:
                print(type(e), ':', e)
            """)

